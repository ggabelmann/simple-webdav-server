package fi.iki.elonen.galleries;

import java.net.URI;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * ?
 */
class Util {
   
   static String smartAppendToUrl(final String url, final String append) {
      if (url.endsWith("/")) {
         return url + append;
      }
      else {
         return url + "/" + append;
      }
   }
   
   static HttpEntity getHttpEntity(final String url) {
      //    System.out.println("here2: " + url);
      try {
         HttpGet httpget = new HttpGet(new URI(url));
         httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:15.0) Gecko/20100101 Firefox/15.0.1");
         httpget.setHeader("Referer", url);
         HttpClient httpclient = new DefaultHttpClient();
         HttpResponse response = httpclient.execute(httpget);
         
         if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("status code [" + response.getStatusLine().getStatusCode() + "]");
         }
         
         HttpEntity entity = response.getEntity();
         if (entity == null) {
            throw new RuntimeException("entity [null]");
         }
         else {
            return entity;
         }
      }
      catch (Exception ex1) {
         throw new RuntimeException(ex1);
      }
   }
   
   static long contentLength(final String url) {
      //    System.out.println("here3: " + url);
      HttpResponse response = null;
      try {
         final HttpHead httphead = new HttpHead(new URI(url));
         httphead.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:15.0) Gecko/20100101 Firefox/15.0.1");
         httphead.setHeader("Referer", url);
         HttpClient httpclient = new DefaultHttpClient();
         response = httpclient.execute(httphead);
      }
      catch (Exception ex1) {
         throw new RuntimeException(ex1);
      }
      
      if (response.getStatusLine().getStatusCode() != 200) {
         throw new RuntimeException("status code [" + response.getStatusLine().getStatusCode() + "]");
      }
      
      final Header[] contentLengths = response.getHeaders("content-length");
      if (contentLengths.length == 0) {
         throw new RuntimeException("no content-length");
      }
      else {
         return Long.parseLong(contentLengths[0].getValue());
      }
   }
   
}
