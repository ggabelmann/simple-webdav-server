package handler;

import java.util.Map;

import fi.iki.elonen.NanoHTTPD.Method;
import fi.iki.elonen.NanoHTTPD.Response;

/**
 * ?
 */
class CombinedHandler implements Handler {
   
   private final Handler handler;
   private String headerKey;
   private String headerRegex;
   private final Method m;
   private final String uriRegex;
   
   /**
    * Constructor.
    * 
    * @param uriRegex May be null.
    * @param method May be null.
    * @param handlers May be null.
    */
   CombinedHandler(final String uriRegex, final Method method, final String header, final String headerRegex, final Handler... handlers) {
      this.handler = new ArrayOfHandlersHandler(handlers);
      this.headerKey = header;
      this.headerRegex = headerRegex;
      this.uriRegex = uriRegex;
      this.m = method;
   }
   
   @Override
   public Response handle(final String uri, final Method method, final Map<String, String> header, final Map<String, String> parms) {
      if (  (uriRegex == null || uri.matches(uriRegex)) &&
            (m == null || method.equals(m)) &&
            (headerKey == null || (header.get(headerKey) != null && header.get(headerKey).matches(headerRegex)))) {
         return handler.handle(uri, method, header, parms);
      }
      else {
         return null;
      }
   }
   
}
