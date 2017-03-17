---
layout: post
title:  Guava EventBus
date:   2017-03-15
categories: guava
tags: guava eventbus util gradle
---

[Guava][guava] is Google's utilities, collections and helpers library for Java. Similar in spirit to the [Apache Commons][apache] set of libraries, but possibly a bit more pragmatic. Google's engineers use the Guava components in their daily work, and have fine-tuned the APIs to boost their own productivity. The current release is version 21.

In this article, we'll look at the Guava [EventBus][eventbus] class, for publishing and subscribing to application wide events of all kinds. It strikes a good balance between convenience and type-safety, and is much more lightweight than rolling your own Event, Handler and Fire classes. See also the [Guava documentations][EventBusExplained] for further details.

To have an example to work with, the following *Player* skeleton class is implemented. It comes with three separate Event classes, which are very minimal in this contrived example. In a real application, they might inherit from a super-Event class, and possibly carry some content, at least the source of the event or similar. Alternatively, they could have been made simpler, by being elements of an enumeration, however, then we'd have to do manual matching and routing on the receiving end.

Notice, that the receiving part of the EventBus events is already in place, in the form of the *@Subscribe* annotations.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/guava/EventBusTest.java' method='class Player' before=0  after=6 %}
{% endhighlight %}

In the following code, the *EventBus* is created, and the *Player* is registered as a receiver. To publish events, all that is required is to pass objects of the same types to the *EventBus.post()* method. The internals of the *EventBus* will invoke all methods which are marked with the *@Subscribe* annotation and match the exact type. Event types are not inherited, so a catch-all subscriber is not possible, which is probably a good design choice.

The *EventBus* also makes unit testing easier, since sender and receiver can be decoupled without extensive mocking. In fact, we now have a choice of testing the methods of the *Player* class through the *Eventbus*, or by invoking its methods directly. Either might be fine when writing unit tests. Although, for component level test, sticking with the *EventBus* is probably better, as it will be closer to the live application.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/guava/EventBusTest.java' method='void setup' before=4  after=0 %}
{% include includemethod filename='src/com/rememberjava/guava/EventBusTest.java' method='void testPlay' before=1  after=0 %}
{% endhighlight %}

To include the Guava library from the Maven Gradle repository, this will suffice.

{% highlight shell %}
repositories {
  mavenCentral()
}

dependencies {
  compile 'com.google.guava:guava:21.0'
}
{% endhighlight %}

Here is the full code listing.

{% include javafile filename='src/com/rememberjava/guava/EventBusTest.java' %}

[EventBusExplained]: https://github.com/google/guava/wiki/EventBusExplained
[eventbus]: https://google.github.io/guava/releases/21.0/api/docs/com/google/common/eventbus/EventBus.html
[guava]: https://github.com/google/guava
[apache]: https://commons.apache.org/
