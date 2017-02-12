---
layout: post
title:  A failed attempt at a WebSocket server
date:   2017-01-25 07:45:02 +0100
categories: websocket
tags: websocket
---

Following the [post about the small HTTP server][http-post] using the the [*com.sun.net.httpserver*][sun-httpserver] API, I thought I'd try to make it work with WebSockets. I'll save the suspense; it wont work. And I'm not the first to have failed at it, but it's always more fun to [search for the solution afterwards][fail]..

The problem is that the httpserver package is not designed for the persistent bi-directional connection used by the [WebSocket protocol][websocket]. The only part which will work is the handshake, which transmits a HTTP like request and response. However, as soon as that response is sent, the connection is closed, and thus the WebSocket object in the browser client will close.

There is a [WebSocket API in Java EE][ee-api], however it will require an application server like Glassfish or Tomcat to run. Another option is the [SimpleFramework server][simpleframework], which also includes a WebSocket implementation. More about that in a later post.

The only part of the code worth mentioning here is the handshake response from the server. It sets the correct headers and encodes the server accept key according to the specification. Connecting to this server will therefore result in a valid websocket object on the client, as long as *handle()* method on the server does not return.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/http/CrippledWebSocketHandler.java' method='void handle' before=0  after=0 %}{% include includemethod filename='src/com/rememberjava/http/CrippledWebSocketHandler.java' method='String getServerResponseKey' before=0  after=0 %}
{% endhighlight %}

[http-post]: /http/2017/01/20/simple_http_server.html
[sun-httpserver]: https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/package-summary.html
[websocket]: https://tools.ietf.org/html/rfc6455
[fail]: https://duckduckgo.com/?q=com.sun.net.httpserver+websockets&t=hs&ia=web
[ee-api]: http://www.oracle.com/technetwork/articles/java/jsr356-1937161.html
[simpleframework]: http://www.simpleframework.org/
