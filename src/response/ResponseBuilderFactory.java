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
   public ResponseBuilder addHeader(String name, String value) {
      return create().addHeader(name, value);
   }

   @Override
   public ResponseBuilder content(String content) {
      return create().content(content);
   }

   @Override
   public ResponseBuilder is(InputStream is) {
      return create().is(is);
   }

   @Override
   public ResponseBuilder cr(CollectionResource cr, String host, int port, String depth) {
      return create().cr(cr, host, port, depth);
   }

   @Override
   public ResponseBuilder fr(FileResource fr, String host, int port) {
      return create().fr(fr, host, port);
   }

   @Override
   public ResponseBuilder xml(String content) {
      return create().xml(content);
   }

   @Override
   public ResponseBuilder mime(String mime) {
      return create().mime(mime);
   }

   @Override
   public ResponseBuilder status(Status s) {
      return create().status(s);
   }

   @Override
   public Response build() {
      return create().build();
   }
   
}
