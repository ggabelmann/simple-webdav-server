package handler;

import java.util.Map;

import fi.iki.elonen.NanoHTTPD.Method;
import fi.iki.elonen.NanoHTTPD.Response;

public class ArrayOfHandlersHandler implements Handler {
   
   private final Handler[] handlers;
   
   public ArrayOfHandlersHandler(final Handler... handlers) {
      if (handlers == null) {
         this.handlers = new Handler[0];
      }
      else {
         this.handlers = handlers;
      }
   }
   
   @Override
   public Response handle(String uri, Method method, Map<String, String> header, Map<String, String> parms) {
      Response response = null;
      for (final Handler handler : handlers) {
         response = handler.handle(uri, method, header, parms);
         if (response != null) {
            break;
         }
      }
      
      return response;
   }
   
}
