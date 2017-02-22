---
layout: post
title:  Socket client / server example
date:   2017-02-21
categories: socket
tags: net socket server
---

After posts on several server libraries, including [Sun's HTTP][sun-http]; Simple Framework's [HTTP][sf-http]; and [Websocket][sf-websocket], it seems appropriate to include a plain TCP socket server / client example. Without any application protocol to adhere to, it's straight forward to setup and test. There are two essential classes involved: On the server side there's a [*ServerSocket*][ServerSocket], while the client has a [*Socket*][Socket]. The only difference is that the *ServerSocket* accepts one or more incoming connections, and provide a *Socket* handle for each. After that, both client and server are the same, in that the *Socket* object provides input and output streams once established.

In the code below, a *ServerSocket* is set to listen to a specific port, and to accept a single incoming connection. It grabs the IO streams, makes these available to the rest of the test, and finally releases a semaphore lock to allow the test to continue. Normally, the server thread would wait for further incoming requests, and would probably spawn or assign pooled threads to handle the request. Here we focus only on the basic IO parts.

{% highlight java %}
...{% include includemethod filename='src/com/rememberjava/net/SocketTest.java' method='new ServerSocket' after=1 %}
...
{% include includemethod filename='src/com/rememberjava/net/SocketTest.java' method='void listen' %}
{% endhighlight %}

On the client side, it's just as simple: Establish a connection to the severer host and port, and get the IO streams.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/net/SocketTest.java' method='new Socket' after=2 %}
{% endhighlight %}

The rest of the test code below asserts that messages are received correctly both on the client and the server, using the raw streams and wrapped *PrinterWriter* helpers. Again, he semaphore is used to wait for the server thread to establish its connection before the rest of the test continues. Without it, using the IO streams will results in NullPointerExceptions, since they are not initialized yet.

{% include javafile filename='src/com/rememberjava/net/SocketTest.java' %}

[sun-http]: /http/2017/01/20/simple_http_server.html
[sf-http]: /http/2017/02/12/sf_http_server.html
[sf-websocket]: /websocket/2017/02/18/sf_websocket_server.html
[ServerSocket]: https://docs.oracle.com/javase/8/docs/api/java/net/ServerSocket.html
[Socket]: https://docs.oracle.com/javase/8/docs/api/java/net/Socket.html
