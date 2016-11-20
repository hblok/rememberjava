---
layout: post
title: Fun with IO Streams
date:   2016-11-29 07:12:08 -0700
categories: 
---

With Java 8, file IO became a pleasure to work with. It was a drastic break with the old verbose syntax, and awkward *((line = in.readLine()) != null)* and try/try/close idioms. With the new Stream based methods, and lambda functions, the IO API becomes more similar to Python in ease of use.

In the two lines of code below, all the lines of a file is read, sorted, and printed.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/lambda/StreamFun.java' method='printFile()' before=-1  after=-1 %}
{% endhighlight %}

The following lines does the same, but breaks up the in-line method invocations and returns, to more clearly see what's going on. 

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/lambda/StreamFun.java' method='printFileAlternative()' before=-1  after=-1 %}
{% endhighlight %}


{% highlight java %}
{% include src/com/rememberjava/lambda/StreamFun.java %}
{% endhighlight %}

