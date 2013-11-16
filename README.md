simple-webdav-server
====================

Using HTTP's GET and WebDAV's PROPFIND this project allows for the creation of simple, readonly, filesystems.

PROPFIND comes from the WebDAV spec and allows a client to request metadata about an uri -- things like name, type, size, and if it has children (returned as xml). This is enough to create a simple filesystem of directories and files. A user can "mount" a WebDAV server and treat it like any network filesystem.

So far the only implementation is a "galleries" server. Using the Windows Explorer application in Windows 7 I am able to connect to the server and browse the resulting network drive. Google's Guava library is used to cache metadata and data. The Class is located at https://github.com/ggabelmann/simple-webdav-server/blob/master/src/galleries/GalleriesServer.java.

So, to recap, this project lets you create a virtual network filesystem which can contain any data you like. No HTML or Javascript frontend work necessary.
