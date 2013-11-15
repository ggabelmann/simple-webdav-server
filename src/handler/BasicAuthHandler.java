package fi.iki.elonen.handler;

import java.util.Map;

import fi.iki.elonen.NanoHTTPD.Method;
import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.response.ResponseBuilderFactory;

/**
 * A Handler for Basic authentication.
 * It accepts whatever the client gives it as correct.
 */
public class BasicAuthHandler implements Handler {
   
   private ResponseBuilderFactory rbf;
   
   BasicAuthHandler() {
      this.rbf = new ResponseBuilderFactory();
   }
   
   @Override
   public Response handle(final String uri, final Method method, final Map<String, String> header, final Map<String, String> parms) {
      final String authValue = header.get("authorization");
      if (authValue != null && authValue.contains("asic")) {
         return null;
      }
      else {
         return rbf.addHeader("WWW-Authenticate", "Basic realm=\"basicrealm\"").status(Response.Status.UNAUTHORIZED).build();
      }
   }
   
}
