package fi.iki.elonen.galleries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This Class acts as an Iterator of Strings for text which can have ranges in it.
 * For exmaple: "aa/[1-2]/bb/[03-04]"
 * 
 * aa/1/bb/03
 * aa/1/bb/04
 * aa/2/bb/03
 * aa/2/bb/04
 */
public class RangeIterator implements Iterator<String> {
	
   private int count; // The integer which "generates" the patterns of numbers.
   private final int total;
   final List<Generator> generators;
   
   /**
    * Constructor.
    * 
    * @param line Can be null, empty, or contain a "#" comment character which will be removed.
    */
	public RangeIterator(final String line) {
	   this.generators = new ArrayList<Generator>();
	   this.count = 0;
	   
	   String modifiedLine = line;
      if (modifiedLine == null) {
         this.total = 0;
         return;
      }
      
      if (modifiedLine.contains("#")) {
         modifiedLine = modifiedLine.substring(0, modifiedLine.indexOf("#"));
      }
      
      modifiedLine = modifiedLine.trim();
      if (modifiedLine.length() == 0) {
         this.total = 0;
         return;
      }
      
      final String[] segments = modifiedLine.split("[\\[\\]]");
      
      for (int i = segments.length - 1; i >= 0; i--) {
         final String segment = segments[i];
         final Matcher m = Pattern.compile("(\\d+)\\-(\\d+)").matcher(segment);
         
         if (m.matches()) {
            final String fromString = m.group(1);
            final String toString = m.group(2);
            final int fromInt = Integer.parseInt(fromString);
            final int toInt = Integer.parseInt(toString);
            
            int divisor = 1;
            for (final Generator generator : generators) divisor *= generator.getLength();
            generators.add(new RangeGenerator(fromInt, toInt, fromString.length(), divisor));
         }
         else {
            generators.add(new SingletonGenerator(segment));
         }
      }
	   
      Collections.reverse(generators);
      
      int temp = 1;
      for (final Generator generator : generators) temp *= generator.getLength();
      this.total = temp;
	}
	
	@Override
	public boolean hasNext() {
	   return count < total;
	}

	@Override
	public String next() {
	   final StringBuffer sb = new StringBuffer();
      for (final Generator generator : generators) sb.append(generator.get(count));
      count++;
      return sb.toString();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
   private static interface Generator {
      
      public String get(final int i);
      
      public int getLength();
      
   }
   
   private static class SingletonGenerator implements Generator {
      
      private final String text;
      
      private SingletonGenerator(final String text) {
         this.text = text;
      }
      
      public String get(final int i) {
         return text;
      }
      
      public int getLength() {
         return 1;
      }
      
   }
   
   private static class RangeGenerator implements Generator {
      
      private final int from;
      private final int to;
      private final int paddedLength;
      private final int divisor;
      
      private RangeGenerator(final int from, final int to, final int paddedLength, final int divisor) {
         this.from = from;
         this.to = to;
         this.paddedLength = paddedLength;
         this.divisor = divisor;
      }
      
      public String get(final int i) {
         String result = Integer.toString(((i / divisor) % getLength()) + from);
         while (result.length() < paddedLength) result = "0" + result;
         return result;
      }
      
      public int getLength() {
         return to - from + 1;
      }
      
   }
   
}
