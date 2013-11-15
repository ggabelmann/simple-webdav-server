simple-webdav-server
====================

This project is an experiment in creating custom web apps which use HTTP GET and PROPFIND.

So far the only implementation is a "galleries" server. It is able to serve galleries to my Windows 7 Windows Explorer. (That is, photos from the web appear as a network drive.). Google's Guava library is used to cache metadata and data. The Class is located at https://github.com/ggabelmann/simple-webdav-server/blob/master/src/galleries/GalleriesServer.java.
