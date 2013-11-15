package fi.iki.elonen.response;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import fi.iki.elonen.Mime;
import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.resource.CollectionResource;
import fi.iki.elonen.resource.CollectionResourceRenderer;
import fi.iki.elonen.resource.FileResource;
import fi.iki.elonen.resource.FileResourceRenderer;

class ResponseBuilderImpl implements ResponseBuilder {
   
   private String content;
   private final List<String[]> headers;
   private InputStream is;
   private String mime;
   private Response.Status status;
   
   /**
    * Constructor.
    */
   ResponseBuilderImpl() {
      this.headers = new ArrayList<String[]>();
   }
   
   @Override
   public ResponseBuilderImpl addHeader(final String name, final String value) {
      headers.add(new String[] {name, value});
      return this;
   }
   
   @Override
   public ResponseBuilderImpl content(final String content) {
      this.content = content;
      return this;
   }
   
   @Override
   public ResponseBuilderImpl is(final InputStream is) {
      this.is = is;
      return this;
   }
   
   @Override
   public ResponseBuilderImpl cr(final CollectionResource cr, final String host, final int port, final String depth) {
      return xml(new CollectionResourceRenderer(cr, host, port).getResponseXml(depth));
   }
   
   @Override
   public ResponseBuilderImpl fr(final FileResource fr, final String host, final int port) {
      return xml(new FileResourceRenderer(fr, host, port).getResponseXml("0"));
   }
   
   @Override
   public ResponseBuilderImpl xml(final String content) {
      return content(content).mime(Mime.TYPES.get("xml")).status(Response.Status.MULTI_STATUS);
   }
   
   @Override
   public ResponseBuilderImpl mime(final String mime) {
      this.mime = mime;
      return this;
   }
   
   @Override
   public ResponseBuilderImpl status(final Response.Status s) {
      this.status = s;
      return this;
   }
   
   @Override
   public Response build() {
      final Response.Status localStatus = status == null ? Response.Status.OK : status;
      final String localMime = mime == null ? Mime.TYPES.get("txt") : mime;
      final String localContent = content == null ? "" : content;
      
      Response r = null;
      if (is == null) {
         r = new Response(localStatus, localMime, localContent);
      }
      else {
         r = new Response(localStatus, localMime, is);
      }
      for (final String[] pair : headers) {
         r.addHeader(pair[0], pair[1]);
      }
      return r;
   }
   
}
