package response;

import java.io.InputStream;

import resource.CollectionResource;
import resource.FileResource;
import fi.iki.elonen.NanoHTTPD.Response;

public interface ResponseBuilder {
   
   public ResponseBuilder addHeader(final String name, final String value);
   
   public ResponseBuilder content(final String content);
   
   public ResponseBuilder is(final InputStream is);
   
   /**
    * Renders a CollectionResource.
    * 
    * @param cr ?
    * @param host ?
    * @param port ?
    * @param depth ?
    * @return ?
    */
   public ResponseBuilder cr(final CollectionResource cr, final String host, final int port, final String depth);
   
   /**
    * Renders a FileResource.
    * 
    * @param fr ?
    * @param host ?
    * @param port ?
    * @return ?
    */
   public ResponseBuilder fr(final FileResource fr, final String host, final int port);
   
   public ResponseBuilder xml(final String content);
   
   public ResponseBuilder mime(final String mime);
   
   public ResponseBuilder status(final Response.Status s);
   
   public Response build();
   
}
