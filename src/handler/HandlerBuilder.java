package handler;

import fi.iki.elonen.NanoHTTPD.Method;
import fi.iki.elonen.NanoHTTPD.Response;

/**
 * ?
 */
public interface HandlerBuilder {
   
   public Handler build();
   
   public Handler handler(final Handler... h);
   
   public HandlerBuilder onHeader(final String header, final String headerRegex);
   
   public HandlerBuilder onMethod(final Method method);
   
   public HandlerBuilder onUri(final String uri);
   
   public Handler response(final Response r);
   
}
