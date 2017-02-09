---
published: false

layout: post
title:  Identity Map Collector Helpers
date:   2017-01-29 20:40:16 +0100
categories: lambda
---

When generating a Map, the basis is very often a list of unique elements (i.e. a Set), and often the original elements take part in the new Map, either as keys or values. When the original elements are keys, we might be talking about a cache, where the key is the pointer and the value is the result of some expensive operation. E.g. the key is a filename and the value is the bytes of the file loaded into memory for fast serving. Conversely, when the original elements are values of the new map, we're often dealing with a look-up table, e.g. from an ID to its User object, or as in the [previous article][previous] from a short-form string to a specific class reference.

Streams iterating over collections, and lambda functions take some of the dull boilerplate out of creating these kind of maps. However, some of the helper classes, like Collectors, come with rather long method names. To take away even these parts, here are a few helper methods for the identity maps. They come in two forms: One which returns a *Collector* which can be used in the *collect()* method of the Stream. Typically, this is useful if other operations, like filtering, is also used. The other helper methods operate directly on the *Collection*, and take only the mapping function for keys or values. See the full file for further examples and documentation below.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/lambda/IdentityCollectorsTest.java' method='Map<T, U>> identityToValue' before=0  after=3 %}{% include includemethod filename='src/com/rememberjava/lambda/IdentityCollectorsTest.java' method='Map<T, U> identityToValue' before=0  after=3 %}
{% endhighlight %}


{% highlight java %}
{% include includemethod filename='src/com/rememberjava/lambda/IdentityCollectorsTest.java' method='Map<K, T>> keytoIdentity' before=0  after=3 %}{% include includemethod filename='src/com/rememberjava/lambda/IdentityCollectorsTest.java' method='Map<K, T> keytoIdentity' before=0  after=3 %}
{% endhighlight %}

The full code listing, with examples and documentation. This code is under GPL3.

{% include javafile filename='src/com/rememberjava/lambda/IdentityCollectorsTest.java' %}


[previous]: /2017/01/27/reflective_construction.html
