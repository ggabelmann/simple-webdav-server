package response;

public class StrongEtag {
   
   private final String etag;
   
   /**
    * Constructs a strong ETag.
    * 
    * @param content Do not wrap it in quotes. Cannot be null.
    */
   public StrongEtag(final String content) {
      this.etag = "\"" + content + "\""; // The double quotes are required according to the RFC.
   }
   
   @Override
   public String toString() {
      return etag;
   }
}
