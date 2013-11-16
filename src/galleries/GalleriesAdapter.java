package galleries;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Iterators;

import resource.CollectionResource;
import resource.FileResource;

class GalleriesAdapter implements CollectionResource {
   
   final Galleries galleries;
   final String uri;
   
   GalleriesAdapter(final Galleries g, final String uri) {
      this.galleries = g;
      this.uri = uri;
   }
   
   @Override
   public String getDisplayName() {
      return "galleries";
   }

   @Override
   public String getUri() {
      return uri;
   }

   @Override
   public Iterator<CollectionResource> getChildCollections() {
      final List<CollectionResource> children = new ArrayList<CollectionResource>();
      for (int i = 0; i < galleries.size(); i++) {
         children.add(new GalleryAdapter(galleries.get(i), uri, false));
      }
      return children.iterator();
   }

   @Override
   public Iterator<FileResource> getChildFiles() {
      return Iterators.emptyIterator();
   }
   
}