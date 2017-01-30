---
published: false

layout: post
title:  Identity Map Collector Helpers
date:   2017-01-29 20:40:16 +0100
categories: lambda
---

When generating a Map, the basis is very often a list of unique elements (i.e. a Set), and often the original elements take part in the new Map, either as keys or values. When the original elements are keys, we might be talking about a cache, where the key is the pointer and the value is the result of some expensive operation. E.g. the key is a filename and the value is the bytes of the file loaded into memory for fast serving. Conversely, when the orginal elements are values, we're often dealing with a look-up table, e.g. from an ID to its User object, or as in the [previous article][yesterday] from a short-form string to a specific class reference.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/lambda/IdentityCollectorsTest.java' method='Map<T, U>> identityToValue' before=0  after=3 %}{% include includemethod filename='src/com/rememberjava/lambda/IdentityCollectorsTest.java' method='Map<T, U> identityToValue' before=0  after=3 %}
{% endhighlight %}


{% highlight java %}
{% include includemethod filename='src/com/rememberjava/lambda/IdentityCollectorsTest.java' method='Map<K, T>> keytoIdentity' before=0  after=3 %}{% include includemethod filename='src/com/rememberjava/lambda/IdentityCollectorsTest.java' method='Map<K, T> keytoIdentity' before=0  after=3 %}
{% endhighlight %}


{% include javafile filename='src/com/rememberjava/lambda/IdentityCollectorsTest.java' %}

