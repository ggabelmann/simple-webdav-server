package handler;

import java.util.Map;

import fi.iki.elonen.NanoHTTPD.Method;
import fi.iki.elonen.NanoHTTPD.Response;

public interface Handler {
   
   public Response handle(final String uri, final Method method, final Map<String, String> header, final Map<String, String> parms);
   
}
