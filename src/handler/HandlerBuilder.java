package fi.iki.elonen.handler;

import fi.iki.elonen.NanoHTTPD.Method;
import fi.iki.elonen.NanoHTTPD.Response;

/**
 * ?
 */
public interface HandlerBuilder {
   
   public Handler handler(final Handler... h);
   
   public Handler response(final Response r);
   
   public HandlerBuilder method(final Method method);
   
   public HandlerBuilder uri(final String uri);
   
   public Handler build();
   
}
