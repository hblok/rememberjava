---
layout: post
title:  Method references kills the Factory class
date:   2017-01-06 17:24:10 +0100
categories: lambda
tags: lambda factory
---

When writing reusable code, we often want it to be as general and flexible as possible. So layers of abstractions are added; generic types; abstract class hierarchies; and let's not forget the [Factory pattern][FP]. Joel Spolsky had a famous rant about the [factory factory factory pattern][FFFP], and it can [get ugly][AF] in the real world as well.

One of the reasons for the often clunky factory class is that it has not been possible to pass methods and constructors. Method references in Java 8 changes that, even for constructors. They can be passed for any class or array, e.g. *String::new*, *String[]::new*. Combined with generics, the type of the newly created object can also be specified.

In the example class below, the constructor happen to take two arguments, first a String and then an int. Therefore, the *BiFunction* function method fits, however, it would probably be more appropriate to define a more specific functional interface, which would also make the code more readable. The return value is of the type T, which should then be the same type as the generated object. The use is demonstrated in the test method below.

The restriction with this setup is of course that the number of arguments to the constructor is fixed. We could write fixes around that as well, but that would require the general class to know something about the classes it is instantiating, which defetes the purpose. There's always the old Factory class, though.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/lambda/GenericConstructor.java' method='class Generator' before=0  after=0 %}
{% include includemethod filename='src/com/rememberjava/lambda/GenericConstructor.java' method='testGenerateA' before=0  after=0 %}
{% endhighlight %}

Now, it can be argued that this still constitutes a factory pattern, even if an external factory class is not used. The methods of [Collectors][Collectors] highlights this, e.g.:

{% highlight java %}
    public static <T, C extends Collection<T>>
    Collector<T, ?, C> toCollection(Supplier<C> collectionFactory) {
        return new CollectorImpl<>(collectionFactory, Collection<T>::add,
                                   (r1, r2) -> { r1.addAll(r2); return r1; },
                                   CH_ID);
    }
{% endhighlight %}


The full code listing of the example:

{% include javafile filename='src/com/rememberjava/lambda/GenericConstructor.java' %}

[FP]: https://en.wikipedia.org/wiki/Factory_method_pattern
[FFFP]: http://discuss.joelonsoftware.com/?joel.3.219431.12
[AF]: http://stackoverflow.com/a/2632054
[Collectors]: https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html
