package galleries;

import handler.DigestAuthHandler;
import handler.Handler;
import handler.HandlerBuilderFactory;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import response.ResponseBuilderFactory;
import response.StrongEtag;
import web.Mime;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import fi.iki.elonen.NanoHTTPD;

/**
 * ?
 */
public class GalleriesServer extends NanoHTTPD {
   
   private final HandlerBuilderFactory hbf;
   private final ResponseBuilderFactory rbf;
   private final Handler handler;
   private final Galleries galleries;
   private final LoadingCache<String, byte[]> imageData;
   private final LoadingCache<String, Image> images;
   private final boolean quiet;
   
   public static void main(String[] args) {
      int port = 8080;
      String host = "127.0.0.1";
      boolean quiet = false;
      File galleriesFile = null;
      
      // Parse command-line, with short and long versions of the options.
      for (int i = 0; i < args.length; ++i) {
         if (args[i].equalsIgnoreCase("-h") || args[i].equalsIgnoreCase("--host")) {
            host = args[i + 1];
         } else if (args[i].equalsIgnoreCase("-p") || args[i].equalsIgnoreCase("--port")) {
            port = Integer.parseInt(args[i + 1]);
         } else if (args[i].equalsIgnoreCase("-q") || args[i].equalsIgnoreCase("--quiet")) {
            quiet = true;
         } else if (args[i].equalsIgnoreCase("-g") || args[i].equalsIgnoreCase("--galleries")) {
            galleriesFile = new File(args[i + 1]).getAbsoluteFile();
         }
      }
      
      if (galleriesFile == null) {
         System.err.println("galleries must be set.");
         System.exit(-1);
      }
      
      final GalleriesServer server = new GalleriesServer(host, port, quiet, galleriesFile);
      try {
         server.start();
      }
      catch (IOException ioe) {
         System.err.println("Couldn't start server:\n" + ioe);
         System.exit(-1);
      }
      System.out.println("Server started, Hit Enter to stop.\n");
      try {
         System.in.read();
      }
      catch (Throwable ignored) {
      }
      server.stop();
      System.out.println("Server stopped.\n");
   }
   
   public GalleriesServer(String host, int port, boolean quiet, File galleriesFile) {
      super(host, port);
      
      this.hbf = new HandlerBuilderFactory();
      this.rbf = new ResponseBuilderFactory();
      this.galleries = new Galleries();
      this.quiet = quiet;
      
      this.imageData = CacheBuilder.newBuilder()
            .maximumSize(50)
            .build(new CacheLoader<String, byte[]>() {
               public byte[] load(String url) throws IOException {
                  final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                  Util.getHttpEntity(url).writeTo(baos);
                  return baos.toByteArray();
               }
            });
      
      this.images = CacheBuilder.newBuilder()
            .maximumSize(500)
            .build(new CacheLoader<String, Image>() {
               public Image load(String url) {
                  try {
                     return new Image(imageData, url, Util.contentLength(url));
                  }
                  catch (final Exception ex1) {
                     return new Image(url);
                  }
               }
            });
      
      this.handler = hbf.handler(
            hbf.onMethod(Method.OPTIONS).response(rbf.addHeader("DAV", "1").addHeader("Allow", "OPTIONS,PROPFIND").build()),
            new DigestAuthHandler(
               hbf.onUri(".+/[aA]uto[rR]un.inf|.+/[dD]esktop.ini|.+/[fF]older.gif|.+/[fF]older.jpg|.+/[tT]humbs.db").response(rbf.status(Response.Status.GONE).build()),
               hbf.onMethod(Method.LOCK).handler(new LockHandler()),
               hbf.onMethod(Method.PROPFIND).handler(
                     hbf.onUri("/galleries/?").handler(new GalleriesHandler()),
                     hbf.onUri("/galleries/gallery\\d+/?").handler(new GalleryHandler())
               ),
               hbf.onUri("/galleries/gallery\\d+/\\d+.jpg").handler(new PhotoHandler())
            ),
            hbf.response(rbf.status(Response.Status.FORBIDDEN).build())
      );
      
      load(galleriesFile);
   }
   
   @Override
   public Response serve(String uri, Method method, Map<String, String> header, Map<String, String> parms) {
      if (quiet == false) {
         System.out.println(method + " '" + uri + "' ");
         
         for (final Map.Entry<String, String> entry : header.entrySet()) {
            System.out.println("  HDR: '" + entry.getKey() + "' = '" + entry.getValue() + "'");
         }
         for (final Map.Entry<String, String> entry : parms.entrySet()) {
            System.out.println("  PRM: '" + entry.getKey() + "' = '" + entry.getValue() + "'");
         }
      }
      
      final Response r = handler.handle(uri, method, header, parms);
      return r;
   }
   
   /**
    * Expands each line in the given file, tests for inclusion, and then creates one gallery per image.
    * 
    * @param galleriesFile Cannot be null.
    */
   private void load(File galleriesFile) {
      try {
         LineNumberReader lnr = new LineNumberReader(new BufferedReader(new FileReader(galleriesFile)));
         int i = 0;
         String line = lnr.readLine();
         
         while (line != null) {
            final Iterator<String> it = new RangeIterator(line);
            while (it.hasNext()) {
               final String url = it.next();
               if (url.hashCode() % 1 == 0) { // Include all lines.
                  System.out.println(url);
                  galleries.add(new Gallery(url, "gallery" + i, images));
                  i++;
               }
            }
            line = lnr.readLine();
         }
         lnr.close();
      }
      catch (IOException ex1) {
         throw new RuntimeException(ex1);
      }
   }
   
   // ===
   
   private class LockHandler implements Handler {
      
      @Override
      public Response handle(String uri, Method method, Map<String, String> header, Map<String, String> parms) {
         return rbf.xml("<?xml version=\"1.0\" encoding=\"utf-8\" ?><D:multistatus xmlns:D=\"DAV:\"><D:response><D:href>" +
            hostname + ":" + myPort + uri + "</D:href><D:status>" + Response.Status.FORBIDDEN.getHttpAndDescription() +
            "</D:status></D:response></D:multistatus>").build();
      }
      
   }
   
   private class GalleriesHandler implements Handler {
      
      @Override
      public Response handle(String uri, Method method, Map<String, String> header, Map<String, String> parms) {
         return rbf.cr(new GalleriesAdapter(galleries, uri), header.get("depth")).build();
      }
      
   }
   
   private class GalleryHandler implements Handler {
      
      @Override
      public Response handle(String uri, Method method, Map<String, String> header, Map<String, String> parms) {
         final Matcher m = Pattern.compile(".+gallery(\\d+).*").matcher(uri);
         m.matches();
         final int galleryNum = Integer.parseInt(m.group(1));
         return rbf.cr(new GalleryAdapter(galleries.get(galleryNum), uri, true), header.get("depth")).build();
      }
      
   }
   
   private class PhotoHandler implements Handler {
      
      private final Pattern pattern;
      
      private PhotoHandler() {
         pattern = Pattern.compile(".+gallery(\\d+)/(\\d+).jpg");
      }
      
      private int getGalleryNum(final String uri) {
         final Matcher m = pattern.matcher(uri);
         m.matches();
         return Integer.parseInt(m.group(1));
      }
      
      protected int getImageNum(final String uri) {
         final Matcher m = pattern.matcher(uri);
         m.matches();
         return Integer.parseInt(m.group(2));
      }
      
      protected Image getImage(final String uri) {
         final int galleryNum = getGalleryNum(uri);
         final int imageNum = getImageNum(uri);
         return galleries.get(galleryNum).getImage(imageNum);
      }
      
      @Override
      public Response handle(String uri, Method method, Map<String, String> header, Map<String, String> parms) {
         final Image image = getImage(uri);
         if (image.success) {
            final StrongEtag etag = new StrongEtag(String.valueOf(image.url.hashCode()));
            final Handler handler = hbf.handler(
                  hbf.onHeader("if-none-match", etag.toString()).response(rbf.status(Response.Status.NOT_MODIFIED).build()), // TODO: do the etag quotes match properly?
                  hbf.onMethod(Method.PROPFIND).response(rbf.fr(new ImageAdapter(image, getImageNum(uri) + ".jpg", uri)).build()),
                  hbf.onMethod(Method.GET).handler(
                        hbf.onHeader("user-agent", ".*[cC]yber[dD]uck.*").response(rbf.addHeader("Location", image.url).status(Response.Status.TEMPORARY_REDIRECT).build()),
                        new Handler() {
                           @Override
                           public Response handle(String uri, Method method, Map<String, String> header, Map<String, String> parms) {
                              final InputStream is = image.getInputStream(); // See if we can get the data...
                              if (image.success) {
                                 return rbf.etag(etag).mime(Mime.TYPES.get("jpg")).is(is).build();
                              }
                              return null;
                           }
                        })
                  );
            return handler.handle(uri, method, header, parms);
         }
         return null;
      }
   }
   
}
