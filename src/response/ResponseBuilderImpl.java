package response;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import resource.CollectionResource;
import resource.CollectionResourceRenderer;
import resource.FileResource;
import resource.FileResourceRenderer;
import web.Mime;
import fi.iki.elonen.NanoHTTPD.Response;

class ResponseBuilderImpl implements ResponseBuilder {
   
   private String content;
   private String etag;
   private final List<String[]> headers;
   private InputStream is;
   private String mime;
   private Response.Status status;
   
   /**
    * Constructor.
    */
   ResponseBuilderImpl() {
      this.headers = new ArrayList<String[]>();
   }
   
   @Override
   public ResponseBuilderImpl addHeader(final String name, final String value) {
      headers.add(new String[] {name, value});
      return this;
   }
   
   @Override
   public ResponseBuilderImpl content(final String content) {
      this.content = content;
      return this;
   }
   
   @Override
   public ResponseBuilderImpl is(final InputStream is) {
      this.is = is;
      return this;
   }
   
   @Override
   public ResponseBuilderImpl cr(final CollectionResource cr, final String depth) {
      return xml(new CollectionResourceRenderer(cr).getResponseXml(depth));
   }
   
   @Override
   public ResponseBuilderImpl fr(final FileResource fr) {
      return xml(new FileResourceRenderer(fr).getResponseXml("0"));
   }
   
   @Override
   public ResponseBuilderImpl mime(final String mime) {
      this.mime = mime;
      return this;
   }
   
   @Override
   public ResponseBuilderImpl status(final Response.Status s) {
      this.status = s;
      return this;
   }
   
   @Override
   public ResponseBuilderImpl xml(final String content) {
      return content(content).mime(Mime.TYPES.get("xml")).status(Response.Status.MULTI_STATUS);
   }
   
   @Override
   public Response build() {
      final Response.Status localStatus = status == null ? Response.Status.OK : status;
      final String localMime = mime == null ? Mime.TYPES.get("txt") : mime;
      final String localContent = content == null ? "" : content;
      
      Response r = null;
      if (is == null) {
         r = new Response(localStatus, localMime, localContent);
      }
      else {
         r = new Response(localStatus, localMime, is);
      }
      for (final String[] pair : headers) {
         r.addHeader(pair[0], pair[1]);
      }
      if (etag != null) {
         r.addHeader("ETag", "\"" + etag + "\""); // The double quotes are required according to the RFC.
      }
      return r;
   }

   @Override
   public ResponseBuilder strongEtag(final String content) {
      this.etag = content;
      return this;
   }
   
}
