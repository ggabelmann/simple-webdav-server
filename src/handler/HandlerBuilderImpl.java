package fi.iki.elonen.handler;

import java.util.Map;

import fi.iki.elonen.NanoHTTPD.Method;
import fi.iki.elonen.NanoHTTPD.Response;

class HandlerBuilderImpl implements HandlerBuilder {
   
   private Handler handler;
   private Method method;
   private String uri;
   
   HandlerBuilderImpl() {
   }
   
   @Override
   public Handler handler(final Handler... h) {
      this.handler = new ArrayOfHandlersHandler(h);
      return build();
   }
   
   @Override
   public Handler response(final Response r) {
      this.handler = new Handler() {
         @Override
         public Response handle(String uri, Method method, Map<String, String> header, Map<String, String> parms) {
            return r;
         }
      };
      return build();
   }
   
   @Override
   public HandlerBuilderImpl method(final Method method) {
      this.method = method;
      return this;
   }
   
   @Override
   public HandlerBuilderImpl uri(final String uri) {
      this.uri = uri;
      return this;
   }
   
   @Override
   public Handler build() {
      return new CombinedHandler(uri, method, handler);
   }
   
}
