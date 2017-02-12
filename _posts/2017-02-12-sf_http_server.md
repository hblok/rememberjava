---
layout: post
title:  Simple Framework HTTP server
date:   2017-02-12
categories: http
tags: http server
---

A while back, I [ranted][sf-example] about the [Simple Framework][simpleframework] [example code][chat-demo] and its unnecessary complexity. It turns out that their HTTP server library is indeed simple, if you just ignore the Spring setup and their example. In fact, as this static file server example shows, it's very similar to the "hidden" [Sun HTTP handler API][http-server].

The only interface which has to be implemented is the *Container*, which is equivalent to the request *HttpHandler* in the Sun HTTP library. The *handle()* method takes a normal HTTP request, and the response code and data can be written via the Response object. The first code block below shows a simple handler.

The next block shows how to tie the required Simple Framework classes together to start the server. The *Container* just mentioned goes into a *ContainerSocketProcessor* where concurrent processing can be setup up. The *processor* goes into a *SocketConnection* which connects to a socket on a given host and port, and the server runs.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/http/SfHttpServerTest.java' method='class StaticFileContainer' before=0  after=0 %}
{% endhighlight %}

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/http/SfHttpServerTest.java' method='setup()' before=0  after=0 %}
{% endhighlight %}

Here is the full server example, started and verified as a unit test.

{% include javafile filename='src/com/rememberjava/http/SfHttpServerTest.java' %}

[sf-example]: /websocket/2017/01/26/simpleframework_chat_example.html
[http-server]: /http/2017/01/20/simple_http_server.html
[simpleframework]: http://www.simpleframework.org/
[chat-demo]: https://github.com/ngallagher/simpleframework/tree/master/simple-demo/simple-demo-chat
