---
layout: post
title: Fun with IO Streams
date:   2016-11-21 07:12:08 -0700
categories: lambda streams
tags: lambda streams io
---

With Java 8, file IO became a pleasure to work with. It was a drastic break from the old verbose syntax and awkward *((line = in.readLine()) != null)* and try/try/close idioms. With the new Stream based methods and lambda functions, the IO API becomes more similar to Python in ease of use.

In the code snippet below, all the lines of a file is read, sorted, and printed.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/lambda/StreamFun.java' method='printFile()' before=-1  after=-1 %}
{% endhighlight %}

The following lines do the same, but break up the in-line method invocations and returned objects, to more clearly see what's going on. 

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/lambda/StreamFun.java' method='printFileAlternative()' before=-1  after=-1 %}
{% endhighlight %}

Traversing the directory hierarchy is similarly easy and compact:

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/lambda/StreamFun.java' method='walkDirectories()' before=-1  after=-1 %}
{% endhighlight %}

Finally, here's an idea for an *assertFileEquals()* method. It takes two file names, one which is the expected "golden" file which would contain the output from the test. (The example file names below are to make sure the test passes).

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/lambda/StreamFun.java' method='testGoldenFile()' before=1  after=12 %}
{% endhighlight %}

Finally, here's the full test file listing.

{% include javafile filename='src/com/rememberjava/lambda/StreamFun.java' %}
