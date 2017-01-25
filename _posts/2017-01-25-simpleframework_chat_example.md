---
published: false

layout: post
title:  WebSocket Chat example with the SimpleFramework API
date:   2017-01-25 18:08:32 +0100
categories: websocket
---

As mentioned in the [previous post][fail], the [SimpleFramework API and server][simpleframework] is a minimal alternative in the world of Java application servers like Glassfish, Tomcat. [Hosted on GitHub][sf-gh] under an Apache v2.0 license, they offer an embeddable HTTP, Servlet and WebSocket capable stack and API. They claim their performance is better than other Java based servers.

Whether they live up to their name, and is truly easy and simple to use is somewhat debatable, though. They favour [Spring][spring] for dependency injection, which means part of the code is specified in XML, and is "magically" coupled during runtime. There might be benefits to that, but the downside is that it becomes difficult to read and debug. In fact, their demo application will not start for whatever reason, and all that is offered in terms of output is an *InvalidArgumentException* deep down from the Spring code.

Don't get me wrong, the [Google Guice][guice] framework is not much better. It might specify its module coupling in Java code instead of XML, however, when it comes to following the code and debugging, it is just as obscure. Implementations tend to be hidden behind layers of interfaces. As for the promise of easily re-wireable code, they both fall short. When it comes down to it, a refactoring will typically involve ...




{% highlight java %}

{% endhighlight %}

[fail]: /websocket/2017/01/25/failed_websocket.html
[simpleframework]: http://www.simpleframework.org/
[sf-gh]: https://github.com/ngallagher/simpleframework
[spring]: https://en.wikipedia.org/wiki/Spring_Framework
[guice]: https://en.wikipedia.org/wiki/Google_Guice
