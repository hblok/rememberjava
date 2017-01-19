---
layout: post
title:  Limited size queue
date:   2017-01-04 11:51:25 +0100
categories: apache queue
---

The Java Collections [Queue][QUEUE] implementations will either grow without limit, or block if it grows beyond a given size, like the [LinkedBlockingDeque][LBD]. However, what if you need a non-blocking queue which drops its oldest elements? The Apache Commons [CircularFifoQueue][CFQ] covers that. The snippet below shows typical use, with a queue size of two, and where the first element of three is dropped.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/apache/CircularFifoQueueTest.java' method='testDrop()' before=0  after=0 %}
{% endhighlight %}

To install the Apache Commons 4.0 library on Debian / Ubuntu:

{% highlight shell %}
apt-get install libcommons-collections4-java libcommons-collections4-java-doc
apt-get source libcommons-collections4-java
{% endhighlight %}

The relevant files will be located at:  
{% highlight shell %}
/usr/share/java/commons-collections4.jar  
/usr/share/maven-repo/org/apache/commons/commons-collections4/4.0/commons-collections4-4.0-javadoc.jar
{% endhighlight %}

Often, a queue is populated on one thread, and consumed by another. In this case, the access methods have to be synchronized, as seen in this example. Both *offer()* and *poll()* methods are non-blocking, and *null* is returned if the queue is empty.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/apache/CircularFifoQueueTest.java' method='testMultithreaded()' before=0  after=0 %}
{% endhighlight %}

Finally, how does this queue work with Streams? In a single-thread context, there shouldn't be a problem. However, when multithreaded it gets more tricky. The example below fails since the two threads operate on the queue concurrently, and a *NoSuchElementException* is often thrown. The [ConcurrentLinkedQueue][CLQ] is thread-safe, but unbounded. Furthermore, its documentation states that *"the size method is NOT a constant-time operation. Because of the asynchronous nature of these queues, determining the current number of elements requires a traversal of the elements, and so may report inaccurate results if this collection is modified during traversal".* Which means we're back to square one.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/apache/CircularFifoQueueTest.java' method='streamFail()' before=0  after=0 %}
{% endhighlight %}

There are a few work-arounds, mentioned in [this discussion][DISCUSSION]. One trick is to use the *Stream.generate()* method, which will loop indefinilty, and synchronize on the queue within. The problem is, that this will never stop, which might be okey depending on your application. However, you'd have to run this on a spearate thread. Alternativly, use the *limit()* method or a stream terminating operation (e.g. *findFirst()*).

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/apache/CircularFifoQueueTest.java' method='streamGenerate()' before=0  after=0 %}
{% endhighlight %}

Also worth mentioning, is the Google Guava implementation [EvictingQueue][EQ]. However, it it also not thread-safe.

Here's the full listing with all test methods.

{% include javafile filename='src/com/rememberjava/apache/CircularFifoQueueTest.java' %}

[QUEUE]: https://docs.oracle.com/javase/8/docs/api/java/util/Queue.html
[LBD]: https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/LinkedBlockingDeque.html
[CFQ]:  http://commons.apache.org/proper/commons-collections/javadocs/api-release/org/apache/commons/collections4/queue/CircularFifoQueue.html
[CLQ]: http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ConcurrentLinkedQueue.html
[DISCUSSION]: http://stackoverflow.com/questions/23462209/stream-api-and-queues-in-java-8#23464195
[EQ]: https://google.github.io/guava/releases/18.0/api/docs/com/google/common/collect/EvictingQueue.html

