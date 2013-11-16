package handler;

import java.util.Map;

import fi.iki.elonen.NanoHTTPD.Method;
import fi.iki.elonen.NanoHTTPD.Response;

/**
 * ?
 */
class CombinedHandler implements Handler {
   
   private final Handler handler;
   private final Method m;
   private final String regex;
   
   /**
    * Constructor.
    * 
    * @param regex May be null.
    * @param method May be null.
    * @param handlers May be null.
    */
   CombinedHandler(final String regex, final Method method, final Handler... handlers) {
      this.handler = new ArrayOfHandlersHandler(handlers);
      this.regex = regex;
      this.m = method;
   }
   
   @Override
   public Response handle(final String uri, final Method method, final Map<String, String> header, final Map<String, String> parms) {
      if ((regex == null || uri.matches(regex)) && (m == null || method.equals(m))) {
         return handler.handle(uri, method, header, parms);
      }
      else {
         return null;
      }
   }
   
}
