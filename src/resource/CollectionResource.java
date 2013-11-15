package fi.iki.elonen.resource;

import java.util.Iterator;

public interface CollectionResource {
   
   public Iterator<CollectionResource> getChildCollections();
   
   public Iterator<FileResource> getChildFiles();
   
   public String getDisplayName();
   
   public String getUri();
   
}
