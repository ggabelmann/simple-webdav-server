package response;

import java.io.InputStream;

import resource.CollectionResource;
import resource.FileResource;
import fi.iki.elonen.NanoHTTPD.Response;

public interface ResponseBuilder {
   
   public ResponseBuilder addHeader(final String name, final String value);
   
   public ResponseBuilder content(final String content);
   
   /**
    * @param content Can be null.
    * @return This ResponseBuilder.
    */
   public ResponseBuilder etag(final StrongEtag etag);
   
   /**
    * If this method is called without a null parameter then the response will use it instead of any value set with content().
    * 
    * @param is Can be null.
    * @return This ResponseBuilder.
    */
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
   public ResponseBuilder cr(final CollectionResource cr, final String depth);
   
   /**
    * Renders a FileResource.
    * 
    * @param fr ?
    * @param host ?
    * @param port ?
    * @return ?
    */
   public ResponseBuilder fr(final FileResource fr);
   
   public ResponseBuilder xml(final String content);
   
   public ResponseBuilder mime(final String mime);
   
   public ResponseBuilder status(final Response.Status s);
   
   public Response build();
   
}
