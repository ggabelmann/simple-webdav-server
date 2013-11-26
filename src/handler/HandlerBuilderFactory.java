package handler;

import fi.iki.elonen.NanoHTTPD.Method;
import fi.iki.elonen.NanoHTTPD.Response;

/**
 * ?
 */
public class HandlerBuilderFactory implements HandlerBuilder {
   
   @Override
   public Handler build() {
      return create().build();
   }

   public HandlerBuilder create() {
      return new HandlerBuilderImpl();
   }

   @Override
   public Handler handler(final Handler... h) {
      return create().handler(h);
   }
   
   @Override
   public HandlerBuilder onHeader(final String header, final String headerRegex) {
      return create().onHeader(header, headerRegex);
   }
   
   @Override
   public HandlerBuilder onMethod(final Method method) {
      return create().onMethod(method);
   }

   @Override
   public HandlerBuilder onUri(final String uri) {
      return create().onUri(uri);
   }

   @Override
   public Handler response(final Response r) {
      return create().response(r);
   }

}
