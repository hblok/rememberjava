---
layout: post
title:  Simple Framework Websocket server
date:   2017-02-18
categories: websocket
tags: websocket server gradle
---

Following the [previous post about a HTTP server][sf-http] based on the [Simple Framework library][simpleframework], here's the Websocket use case. The example gets a bit more complicated with both a HTTP handler, Websocket handler and client, but still a lot simpler than the [original example code][chat-demo].

Two interfaces have to be implemented to have a meaningful Websocket server: First, the [*Service*][Service] interface, which gets invoked when a new connection and [*Session*][Session] is established. From here it is possible to register a [*FrameListener*][FrameListener] on the session channel, which is the second interface to implement. The FrameListener methods will get invoked when new Frames, whether it is data or control codes is received. This listener also gets notified about errors and when the session or socket is closed. Two minimal examples of these implementations are shown below, followed by the *setup()* method which ties it all together and starts the server. Notice how [*RouterContainer*][RouterContainer] takes both the HTTP and Websocket handlers, and routes the incoming requests appropriately.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/http/SfWebsocketServerTest.java' method='class WebsocketService' before=0  after=0 %}
{% include includemethod filename='src/com/rememberjava/http/SfWebsocketServerTest.java' method='class WebsocketFrameListener' before=0  after=0 %}
{% include includemethod filename='src/com/rememberjava/http/SfWebsocketServerTest.java' method='setup()' before=0  after=0 %}
{% endhighlight %}

To test the core functionality of the Websocket server, a bit more is needed on the client side. First, a HTTP like handshake has to be requested and the response read, then the client must respond to ping control frames, and finally, it can send frames of data itself.

Below is the method which sends the handshake to the server as specified in the [Websocket RFC 6455][rfc6455]. There's a few things to note: Currently, details like target path, the host, origin are hard-coded. In a normal client, they should be resolved and set in a proper way. The security key is also hard-coded, but should be generated according to the specification. Finally, note that the line ending has to be "\r\n" or 0xd, 0xa in hex. Therefore, when using a PrintWriter to wrap the raw OutputStream, be careful not to use the *println()* methods as they might append the incorrect line termination.

What's useful about a minimal test client like this, is that's it becomes easy to try out these kind of details in isolation, without distractions from other functionality. Although this example does not go as far as asserting the behaviour, it could easily be extended to an in-memory integration test of the server.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/http/SfWebsocketServerTest.java' method='void sendHandshake' before=0  after=0 %}
{% include includemethod filename='src/com/rememberjava/http/SfWebsocketServerTest.java' method='void receiveHandshake' before=0  after=0 %}
{% endhighlight %}

Finally, this test method ties the client together: it first opens the socket connection to the server; gets the IO streams; sends and receives the handshake; starts the ping response handler on a separate thread; sends one frame of its own with a ping, waits for some seconds to observe the server in operation, and finally sends a close operation code before closing the connection.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/http/SfWebsocketServerTest.java' method='void testWebsocketRequest' before=0  after=0 %}
{% endhighlight %}

Here's the full test case listing, which also includes a HTTP request test. A dependency on the Simple Framework library is needed, for example through the Gradle dependency:
{% highlight shell %}
repositories {
  mavenCentral()
}

dependencies {
  compile 'org.simpleframework:simple-http:6.0.1'
}
{% endhighlight %}

{% include javafile filename='src/com/rememberjava/http/SfWebsocketServerTest.java' %}

[sf-http]: /http/2017/02/12/sf_http_server.html
[simpleframework]: http://www.simpleframework.org/
[chat-demo]: https://github.com/ngallagher/simpleframework/tree/master/simple-demo/simple-demo-chat
[Service]: https://github.com/ngallagher/simpleframework/blob/master/simple/simple-http/src/main/java/org/simpleframework/http/socket/service/Service.java
[Session]: https://github.com/ngallagher/simpleframework/blob/master/simple/simple-http/src/main/java/org/simpleframework/http/socket/Session.java
[FrameListener]: https://github.com/ngallagher/simpleframework/blob/master/simple/simple-http/src/main/java/org/simpleframework/http/socket/FrameListener.java
[RouterContainer]: https://github.com/ngallagher/simpleframework/blob/master/simple/simple-http/src/main/java/org/simpleframework/http/socket/service/RouterContainer.java
[rfc6455]: https://tools.ietf.org/html/rfc6455