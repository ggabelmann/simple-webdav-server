package fi.iki.elonen.resource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

abstract class AbstractResourceRenderer implements ResourceRenderer {
   
   protected final String host;
   protected final int port;
   
   protected static final DateFormat dateFormatter;
   protected static final String xml_head;
   protected static final String xml_foot;
   
   static {
      dateFormatter = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
      dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
      dateFormatter.setLenient(false);
      xml_head = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><D:multistatus xmlns:D=\"DAV:\">";
      xml_foot = "</D:multistatus>";
   }
   
   AbstractResourceRenderer(final String host, final int port) {
      this.host = host;
      this.port = port;
   }
   
}
