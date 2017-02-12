---
layout: post
title:  WebSocket Chat example with the SimpleFramework API
date:   2017-01-26
categories: websocket
tags: websocket server
---

As mentioned in the [previous post][fail], the [SimpleFramework API and server][simpleframework] is a minimal alternative in the world of Java application servers like Glassfish, Tomcat. [Hosted on GitHub][sf-gh] under an Apache v2.0 license, they offer an embeddable HTTP, Servlet and WebSocket capable stack and API. They claim their performance is better than other Java based web servers.

<h2>The Dependency Injection Framework Trap</h2>

Whether they live up to their name, and is truly easy and simple to use is somewhat debatable, though. They favour [Spring][spring] for dependency injection, which means part of the code is specified in XML, and is "magically" coupled during runtime. There might be benefits to that, but the downside is that it becomes difficult to read and debug. In fact, their demo application will not start for whatever reason, and all that is offered in terms of output is an *InvalidArgumentException* without further details deep down from the Spring code.

Don't get me wrong, the [Google Guice][guice] framework is not much better. It might specify its module coupling in Java code instead of XML, however, when it comes to following the code and debugging, it is just as obscure. Implementations tend to be hidden behind layers of interfaces. As for the promise of easily re-wireable code, they both fall short. When it comes down to it, a refactoring will typically involve changes in both class content and class relationships, so a dependency injection framework is more likely to get in the way than help.

Finally, when it comes to testability, dependency injection is of course crucial. Being able to inject mocks or other testing objects keeps unit tests small. However, a wiring framework is not needed to describe the class relationships. In fact, my personal experience is that Guice based code is more cumbersome to use in test related code. It takes considerable effort to track down which classes to include in a unit test, and which to leave out, and then to create a test module of it all. At least Spring leaves the class constructors intact and simple.

<h2>Making it work</h2>

So, with that rant out of the way, what does it take to make the Simple Framework [WebSocket Chat Demo][chat-demo] work? My solution was to simply shortcut the [114 line XML Spring configuration][chat-spring], and write the same class relations in Java. The following 20 lines achieves the same as the XML.

Furthermore, it's worth noting that the SimpleFramework is structured in multiple Maven projects, with dependencies between each other, as seen below the code snippet. It highlights another potential problem with the project. A lot of the server code required to make the Chat demo run is in another demo package. It suggest quite a lot of custom code has to be written to use the server API.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/http/SfChatDemoMain.txt' method='main' before=0  after=0 %}
{% endhighlight %}

&nbsp;&nbsp;&nbsp;

The GitHub Maven packages and dependencies at the time of writing:

[simple (core code)](https://github.com/ngallagher/simpleframework/tree/master/simple)<br>
[simple-test (unit tests)](https://github.com/ngallagher/simpleframework/tree/master/simple-test) -> "simple"

[simple-demo (common demo code)](https://github.com/ngallagher/simpleframework/tree/master/simple-demo/simple-demo) -> "simple"<br>
[simple-demo-chat](https://github.com/ngallagher/simpleframework/tree/master/simple-demo/simple-demo-chat) -> "simple", "simple-demo"<br>
[simple-demo-graph](https://github.com/ngallagher/simpleframework/tree/master/simple-demo/simple-demo-graph) -> "simple", "simple-demo"<br>
[simple-demo-rest](https://github.com/ngallagher/simpleframework/tree/master/simple-demo/simple-demo-rest) -> "simple", "simple-demo", "simple-demo-chat"<br>

It is not entirely clear whether the last demo example, "simple-demo-rest", depends on the "simple-chat" project as the [XML suggests](https://github.com/ngallagher/simpleframework/search?utf8=%E2%9C%93&q=bean+ChatRoom&type=Code), or whether the [duplicate ChatRoom class](https://github.com/ngallagher/simpleframework/blob/master/simple-demo/simple-demo-rest/src/main/java/org/simpleframework/demo/rest/ChatRoom.java) was intended to be used. It might be that the XML was copied, but not updated - highlighting the previous point about hard to maintain and read dependency injection code.

Focusing only on the "simple-demo-chat" example then, this would be what it could look like in the Eclipse Package Explorer, with each directory created as a separate Java project.

![Eclipse setup](/images/simpleframework_projects_eclipse.png)

Finally, here's the full main-file hack to make the chat example work. Notice the required helper classes from demo packages.

{% include javafile filename='src/com/rememberjava/http/SfChatDemoMain.txt' %}

[fail]: /websocket/2017/01/25/failed_websocket.html
[simpleframework]: http://www.simpleframework.org/
[sf-gh]: https://github.com/ngallagher/simpleframework
[spring]: https://en.wikipedia.org/wiki/Spring_Framework
[guice]: https://en.wikipedia.org/wiki/Google_Guice
[chat-demo]: https://github.com/ngallagher/simpleframework/tree/master/simple-demo/simple-demo-chat
[chat-spring]: https://github.com/ngallagher/simpleframework/blob/master/simple-demo/simple-demo-chat/etc/spring.xml

