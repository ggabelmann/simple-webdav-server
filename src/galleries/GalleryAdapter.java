package galleries;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Iterators;

import resource.CollectionResource;
import resource.FileResource;

class GalleryAdapter implements CollectionResource {
   
   final Gallery g;
   final String uri;
   final boolean uriHasNameInIt;
   
   GalleryAdapter(final Gallery g, final String uri, final boolean uriHasNameInIt) {
      this.g = g;
      this.uri = uri;
      this.uriHasNameInIt = uriHasNameInIt;
   }
   
   @Override
   public String getDisplayName() {
      return g.getName();
   }

   @Override
   public String getUri() {
      if (uriHasNameInIt) {
         return uri;
      }
      else {
         return Util.smartAppendToUrl(uri, getDisplayName());
      }
   }

   @Override
   public Iterator<CollectionResource> getChildCollections() {
      return Iterators.emptyIterator();
   }

   @Override
   public Iterator<FileResource> getChildFiles() {
      final List<FileResource> children = new ArrayList<FileResource>();
      
      for (int i = 0; i < g.getNumImages(); i++) {
         final Image image = g.getImage(i);
         if (image.success) {
            children.add(new ImageAdapter(image, "" + i, Util.smartAppendToUrl(getUri(), i + ".jpg")));
         }
         else {
            System.out.println("[" + image.url + "] not successful.");
         }
      }
      
      return children.iterator();
   }
   
}