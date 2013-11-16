package galleries;

import java.util.ArrayList;
import java.util.List;

import com.google.common.cache.LoadingCache;


class Gallery {
   
   private final LoadingCache<String, Image> images;
   private final String name;
   private final List<String> urls;
   
   Gallery(final String line, final String name, final LoadingCache<String, Image> images) {
      this.name = name;
      this.urls = new ArrayList<String>();
      this.images = images;
      
      final RangeIterator ri = new RangeIterator(line);
      while (ri.hasNext()) {
         final String url = ri.next();
         urls.add(url);
      }
   }
   
   Image getImage(final int num) {
      return images.getUnchecked(urls.get(num));
   }
   
   int getNumImages() {
      return urls.size();
   }
   
   String getName() {
      return name;
   }
   
}