package fi.iki.elonen.galleries;

import fi.iki.elonen.Mime;
import fi.iki.elonen.resource.FileResource;

class ImageAdapter implements FileResource {
   
   final Image image;
   final String displayName;
   final String uri;
   
   ImageAdapter(final Image image, final String displayName, final String uri) {
      this.image = image;
      this.displayName = displayName;
      this.uri = uri;
   }
   
   @Override
   public String getContentLength() {
      return image.getLength().toString();
   }

   @Override
   public String getDisplayName() {
      return displayName;
   }

   @Override
   public String getMime() {
      return Mime.TYPES.get("jpg");
   }

   @Override
   public String getUri() {
      return uri;
   }
   
}