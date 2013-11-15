package fi.iki.elonen.resource;


public interface ResourceRenderer {
   
   public String getResponseXmlFragment(final String depth);
   
   public String getResponseXml(final String depth);
   
}
