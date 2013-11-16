simple-webdav-server
====================

This project is an experiment in creating custom webapps/filesystems which use HTTP GET and PROPFIND.

PROPFIND comes from the WebDAV spec and allows a client to request metadata about an uri, things like name, type, size, and if it has children (returned as xml). This is enough to create a simple filesystem of directories and files. A user can "mount" a WebDAV server and treat it like any network filesystem.

So far the only implementation is a "galleries" server. It is able to serve galleries to my Windows 7 Windows Explorer. (That is, photos from the web appear as a network drive.). Google's Guava library is used to cache metadata and data. The Class is located at https://github.com/ggabelmann/simple-webdav-server/blob/master/src/galleries/GalleriesServer.java.

So, to recap, this project lets you create a virtual network filesystem which can contain any data you like. No HTML or Javascript frontend work necessary.
