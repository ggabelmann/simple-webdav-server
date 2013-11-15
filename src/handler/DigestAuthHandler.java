package fi.iki.elonen.handler;

import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import fi.iki.elonen.NanoHTTPD.Method;
import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.response.ResponseBuilderFactory;

/**
 * A Handler for Digest authentication.
 * It accepts whatever the client gives it as correct.
 * 
 * This page (https://gist.github.com/usamadar/2912088) has a servlet implementation that was helpful.
 */
public class DigestAuthHandler implements Handler {
   
   private final Handler handler;
   private ResponseBuilderFactory rbf;
   
   public DigestAuthHandler(final Handler... handlers) {
      this.handler = new ArrayOfHandlersHandler(handlers);
      this.rbf = new ResponseBuilderFactory();
   }
   
   @Override
   public Response handle(final String uri, final Method method, final Map<String, String> header, final Map<String, String> parms) {
      final String authValue = header.get("authorization");
      if (authValue != null && authValue.contains("igest")) {
         return handler.handle(uri, method, header, parms);
      }
      else {
         final String realm = "Digest Authentication";
         final String nonce = Long.toHexString(System.currentTimeMillis());
         final String opaque = DigestUtils.md5Hex(realm + nonce);
         final String authenticate = "Digest realm=\"" + realm + "\", nonce=\"" + nonce + "\", opaque=\"" + opaque + "\"";
         return rbf.addHeader("WWW-Authenticate", authenticate).status(Response.Status.UNAUTHORIZED).build();
      }
   }
   
}
