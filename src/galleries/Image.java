package fi.iki.elonen.galleries;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.google.common.cache.LoadingCache;

class Image {
   
   private final LoadingCache<String, byte[]> data;
   final String url;
   private long size;
   boolean success;
   
   Image(final String url) {
      this.data = null;
      this.size = 0;
      this.success = false;
      this.url = url;
   }
   
   Image(final LoadingCache<String, byte[]> data, final String url, final long size) {
      this.data = data;
      this.size = size;
      this.success = true;
      this.url = url;
   }
   
   Long getLength() {
      return size;
   }
   
   InputStream getInputStream() {
      if (success) {
         try {
            return new ByteArrayInputStream(data.get(url));
         }
         catch (final Exception ex1) {
            this.size = 0;
            this.success = false;
         }
      }
      return null;
   }
   
}