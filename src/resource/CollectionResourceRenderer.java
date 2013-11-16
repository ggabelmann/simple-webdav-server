package resource;

import java.util.Date;
import java.util.Iterator;

import fi.iki.elonen.NanoHTTPD.Response;

/**
 * ?
 */
public class CollectionResourceRenderer extends AbstractResourceRenderer {
   
   private final CollectionResource cr;
   
   public CollectionResourceRenderer(final CollectionResource cr) {
      this.cr = cr;
   }
   
   @Override
   public String getResponseXml(final String depth) {
      return xml_head + getResponseXmlFragment(depth) + xml_foot;
   }
   
   @Override
   public String getResponseXmlFragment(final String depth) {
      final String date = dateFormatter.format(new Date());
      
      String content =
      "<D:response>" +
      "<D:href>" + cr.getUri() + "</D:href>" +
      "<D:propstat>" +
      "<D:prop>" +
      "<D:creationdate>" + date + "</D:creationdate>" +
      "<D:displayname>" + cr.getDisplayName() + "</D:displayname>" +
      "<D:getlastmodified>" + date + "</D:getlastmodified>" +
      "<D:resourcetype><D:collection/></D:resourcetype>" +
      "</D:prop>" +
      "<D:status>" + Response.Status.OK.getHttpAndDescription() + "</D:status>" +
      "</D:propstat>" +
      "</D:response>";
      
      if ("1".equals(depth)) {
         final Iterator<CollectionResource> crIt = cr.getChildCollections();
         while (crIt.hasNext()) {
            final CollectionResourceRenderer crr = new CollectionResourceRenderer(crIt.next());
            content = content + crr.getResponseXmlFragment("0");
         }
         
         final Iterator<FileResource> frIt = cr.getChildFiles();
         while (frIt.hasNext()) {
            final FileResourceRenderer frr = new FileResourceRenderer(frIt.next());
            content = content + frr.getResponseXmlFragment("0");
         }
      }
      
      return content;
   }
   
}
