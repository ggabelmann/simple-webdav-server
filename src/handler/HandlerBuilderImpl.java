package handler;

import java.util.Map;

import fi.iki.elonen.NanoHTTPD.Method;
import fi.iki.elonen.NanoHTTPD.Response;

class HandlerBuilderImpl implements HandlerBuilder {
   
   private Handler handler;
   private String header;
   private String headerRegex;
   private Method method;
   private String uriRegex;
   
   HandlerBuilderImpl() {
   }
   
   @Override
   public Handler build() {
      return new CombinedHandler(uriRegex, method, header, headerRegex, handler);
   }
   
   @Override
   public Handler handler(final Handler... h) {
      this.handler = new ArrayOfHandlersHandler(h);
      return build();
   }
   
   @Override
   public HandlerBuilderImpl onHeader(final String header, final String headerRegex) {
      this.header = header;
      this.headerRegex = headerRegex;
      return this;
   }
   
   @Override
   public HandlerBuilderImpl onMethod(final Method method) {
      this.method = method;
      return this;
   }
   
   @Override
   public HandlerBuilderImpl onUri(final String uriRegex) {
      this.uriRegex = uriRegex;
      return this;
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
   
}
