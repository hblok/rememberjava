---
layout: post
title:  A simple HTTP server
date:   2017-01-20 05:50:47 +0100
categories: http
tags: http
---

The unofficial *com.sun* packages which is still part of the main JDK, include a few [convenience classes for running a HTTP server][httpserver-pkg]. However, since these are not part of the official API, there's typically a warning in the IDEs not to use them. The annotation *@SuppressWarnings("restriction")* can also be used to ignore this warning. Besides that, they will work fine.

Here's what it takes to start the server on a given port, and serve static files from a specific directory tree:

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/http/SimpleHttpServer.java' method='void start()' before=0  after=0 %}
{% endhighlight %}

The second argument to the *create()* method, which is currently 0, is the number of queued incoming queries. Setting a higher number here will allow the server to handle more parallel incoming requests, however potentially at the expense of overall throughput.

The *start()* is non-blocking, so a typically server application would have to go into an internal loop.

As for the handler, it listens to requests where the path of the URI starts with the specified string, in this case "/static". It can then use the request URI to map this to hard-coded files, or files with the same name as in this example:

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/http/StaticFileHandler.java' method='void handle' before=0  after=0 %}
{% endhighlight %}

Finally, to test the running server, here's two unit tests. The first downloads a file which exists, and prints its lines. The second requests a file which does not exist. Notice that the 404 return code passed from *sendResponseHeaders()* in the handler, is enough to make the *openStream()* call throw a *FileNotFoundException* on the client side.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/http/SimpleHttpServerTest.java' method='testDownload' before=1  after=0 %}{% include includemethod filename='src/com/rememberjava/http/SimpleHttpServerTest.java' method='testFilenNotFound' before=1  after=0 %}
{% endhighlight %}

Here's the main class:

{% include javafile filename='src/com/rememberjava/http/SimpleHttpServer.java' %}

And here's the Handler for static files:

{% include javafile filename='src/com/rememberjava/http/StaticFileHandler.java' %}

[httpserver-pkg]: https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/package-summary.html
