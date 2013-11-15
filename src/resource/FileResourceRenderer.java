package fi.iki.elonen.resource;

import java.util.Date;

import fi.iki.elonen.NanoHTTPD.Response;

/**
 * ?
 */
public class FileResourceRenderer extends AbstractResourceRenderer {
   
   private final FileResource fr;
   
   public FileResourceRenderer(final FileResource fr, final String host, final int port) {
      super(host, port);
      this.fr = fr;
   }
   
   /**
    * @param depth Ignored.
    */
   @Override
   public String getResponseXml(final String depth) {
      return xml_head + getResponseXmlFragment(depth) + xml_foot;
   }
   
   /**
    * @param depth Ignored.
    */
   @Override
   public String getResponseXmlFragment(final String depth) {
      final String date = dateFormatter.format(new Date());
      
      return
            "<D:response>" +
            "<D:href>" + host + ":" + port + fr.getUri() + "</D:href>" +
            "<D:propstat>" +
            "<D:prop>" +
            "<D:creationdate>" + date + "</D:creationdate>" +
            "<D:displayname>" + fr.getDisplayName() + "</D:displayname>" +
            "<D:getcontentlength>" + fr.getContentLength() + "</D:getcontentlength>" +
            "<D:getcontenttype>" + fr.getMime() + "</D:getcontenttype>" +
            "<D:getlastmodified>" + date + "</D:getlastmodified>" +
            "<D:resourcetype/>" +
            "</D:prop>" +
            "<D:status>" + Response.Status.OK.getHttpAndDescription() + "</D:status>" +
            "</D:propstat>" +
            "</D:response>";
   }

}
