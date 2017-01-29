---
layout: post
title:  Object construction through reflection
date:   2017-01-27 14:51:12 +0100
categories: 
---

In the last post I ranted about [dependency injection framework magic][yesterday], but even custom magic can cause problems. When objects are constructed and invoked through reflection, the execution flow is no longer apparent in the code. Yet, it still has its place in certain applications, typically mapping external input to object instances, be it language parsing or lexing, or DB output to object mapping. But how to best balance between the abstract world of reflected classes and concrete type-safe code?

The key lies in simplicity, and imposing certain constrains. Dynamically resolving classes and recursive reflective construction of a full object a hierarchy is probably not a good idea. Furthermore, as [discussed previously][method-ref], Java 8 method references now makes it cleaner to pass in factory methods, and avoid the Factory Factory Factory trap.

The following snippet shows the reflective construction of an object, given a few constraints: First off, the name of the input token can be mapped to typed classes. In this example that is done with a generated String to Class map, based on the concrete class references we're dealing with. They have to be available at compile time, and at the time of writing the code. The advantage is that we avoid the *Class.forName()* look-up, and the package hierarchy confusion and possible mismatch of tokens and classes. If this is too strict, we can modify the map, e.g. by allowing for case-insensitive matching (by converting both map key and look-up to the same case). Or if there is not a literal mapping, an enum could define the relationship. Either way, the idea is that the code make it clear which classes we plan to deal with, in strongly typed manner.

The next assumption in this example is that the class to be created has only one constructor method, and that it is public. Or if that is not feasible, the constructor to be used could be marked with an Annotation. Just don't go full-on Guice, and you'll be fine.

Finally, we assume that the relevant constructor takes zero or more parameters, and if it does have parameters that they can themselves we represented and created from a single String object. These parameter objects are initialized in a helper method, discussed below.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/reflect/ObjectConstructorTest.java' method='Object construct' before=5  after=0 %}
{% endhighlight %}

The construction of the parameter classes also contains several assumptions and restrictions. As with the first order classes, we limit ourselves to a pre-defined set of classes. This make it possible to define which constructor or factory methods should be used. In this example, the constructor which takes a single String is used, except for the Password class, where a factory method is used, again taking a single String.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/reflect/ObjectConstructorTest.java' method='Object[] createParameters' before=8  after=0 %}
{% endhighlight %}

That is all it takes to construct objects through reflection, including its parameter types. The examples above maintain some type-safety, and also restricts the supported types. Finally, some error handling and messaging is in place, and more could be added to make it very clear which classes and input is allowed.

The full code listing below shows the example classes to be constructed and test code to verify them. 

{% include javafile filename='src/com/rememberjava/reflect/ObjectConstructorTest.java' %}


[yesterday]: /websocket/2017/01/26/simpleframework_chat_example.html
[method-ref]: /lambda/2017/01/06/generic_constructor.html
