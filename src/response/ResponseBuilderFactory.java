package response;

import java.io.InputStream;

import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.NanoHTTPD.Response.Status;
import resource.CollectionResource;
import resource.FileResource;

public class ResponseBuilderFactory implements ResponseBuilder {
   
   public ResponseBuilder create() {
      return new ResponseBuilderImpl();
   }

   @Override
   public ResponseBuilder addHeader(final String name, String value) {
      return create().addHeader(name, value);
   }

   @Override
   public ResponseBuilder content(final String content) {
      return create().content(content);
   }

   @Override
   public ResponseBuilder etag(final StrongEtag etag) {
      return create().etag(etag);
   }
   
   @Override
   public ResponseBuilder is(final InputStream is) {
      return create().is(is);
   }

   @Override
   public ResponseBuilder cr(final CollectionResource cr, final String depth) {
      return create().cr(cr, depth);
   }

   @Override
   public ResponseBuilder fr(final FileResource fr) {
      return create().fr(fr);
   }

   @Override
   public ResponseBuilder xml(final String content) {
      return create().xml(content);
   }

   @Override
   public ResponseBuilder mime(final String mime) {
      return create().mime(mime);
   }

   @Override
   public ResponseBuilder status(final Status s) {
      return create().status(s);
   }

   @Override
   public Response build() {
      return create().build();
   }

}
