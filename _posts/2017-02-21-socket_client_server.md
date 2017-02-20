---
published: false

layout: post
title:  Socket client / server example
date:   2017-02-21
categories: socket
tags: net socket server
---

{% highlight java %}
...{% include includemethod filename='src/com/rememberjava/net/SocketTest.java' method='new ServerSocket' after=1 %}
...
{% include includemethod filename='src/com/rememberjava/net/SocketTest.java' method='void listen' %}
{% endhighlight %}

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/net/SocketTest.java' method='new Socket' after=2 %}
{% endhighlight %}


{% include javafile filename='src/com/rememberjava/net/SocketTest.java' %}

