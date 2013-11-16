package galleries;

import java.util.ArrayList;
import java.util.List;

/**
 * ?
 */
class Galleries {
   
   private final List<Gallery> galleries;
   
   Galleries() {
      this.galleries = new ArrayList<Gallery>();
   }
   
   void add(final Gallery g) {
      galleries.add(g);
   }
   
   Gallery get(final int num) {
      return galleries.get(num);
   }
   
   int size() {
      return galleries.size();
   }
   
}