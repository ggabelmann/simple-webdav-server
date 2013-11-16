package handler;

import fi.iki.elonen.NanoHTTPD.Method;
import fi.iki.elonen.NanoHTTPD.Response;

/**
 * ?
 */
public class HandlerBuilderFactory implements HandlerBuilder {
   
   public HandlerBuilder create() {
      return new HandlerBuilderImpl();
   }

   @Override
   public Handler handler(final Handler... h) {
      return create().handler(h);
   }
   
   @Override
   public Handler response(final Response r) {
      return create().response(r);
   }

   @Override
   public HandlerBuilder method(final Method method) {
      return create().method(method);
   }

   @Override
   public HandlerBuilder uri(final String uri) {
      return create().uri(uri);
   }

   @Override
   public Handler build() {
      return create().build();
   }

}
