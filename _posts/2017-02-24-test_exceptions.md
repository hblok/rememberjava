---
layout: post
title:  Unit test Exceptions
date:   2017-02-24
categories: junit 
tags: junit hamcrest
---

Testing the "happy path" of the code, when everything goes right, is fine, however error handling and Exceptions is just as much a part of the code under test. In fact, it is possibly a more delicate area, since you want an application which degrades gracefully in the event of error. This post goes through different ways of setting expectations on thrown Exceptions.

In the first method below, the old style pre-Junit 4 way of asserting for an Exception is shown. The idea is that the Exception must be thrown, so if the execution reaches the *fail()* statement, that did not happen. The expected path is instead that the catch-block engages, with the expected Exception type. If a different type of Exception, which is not a sub-class of the caught Exception is thrown, it propagates out of the method and the test fails. Although this style is a bit clunky and verbose, it has a few advantages over the annotation-style: You have control over exactly what point in the code you expect the Exception to be thrown; you can inspect the Exception and assert its message; you can set a custom error message.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/junit/ExceptionsTest.java' method='testOldStyle()' %}
{% endhighlight %}

Since Java 6 and Junit 4, annotations become available, and the *@Test* annotation is now the way to declare a test method. It comes with an extra parameter *expected* which takes a *Class* type indicating which Exception is expected to be thrown. This approach is clean and even minimalist. If all there is to a test is a one-liner like shown below, this is a perfectly fine way to declaring the expectation. However, it loses the ability to inspect the message or cause. Furthermore, if the test method contains more lines, there is no way to control or verify from where the Exception originated.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/junit/ExceptionsTest.java' method='annotation()' before=1 %}
{% endhighlight %}

Enter JUnit 4.7, and the [*@Rule* annotation][Rule] and [ExpectedException rule][ExpectedException]. It solves the problem with the *Test* annotation above, but retains a clean way of expressing the assertion. In the example below, the *Rule* annotation makes sure the *thrown* field is initialised a-new before every test method. It can then be used right above the method which is expected to throw an Exception, and can assert on its type and message. The *expectMessage()* method asserts that the message contains rather than equals the expected string.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/junit/ExceptionsTest.java' method='ExpectedException thrown' before=1 %}
{% include includemethod filename='src/com/rememberjava/junit/ExceptionsTest.java' method='testThrown()' before=1 %}
{% endhighlight %}

The *ExpectedException* class comes with some extra assertion methods which takes the popular [Hamcrest][Hamcrest] matchers. In the code below, the *endsWith* matcher is used.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/junit/ExceptionsTest.java' method='CoreMatchers.endsWith' %}
{% include includemethod filename='src/com/rememberjava/junit/ExceptionsTest.java' method='hamcrest()' before=1 %}
{% endhighlight %}

Finally, the *expectCause()* method takes another Hamcrest matcher, where for example the type of the contained cause can be asserted. Notice that the outer exception type can be asserted as well. The *expectCause* assertion only goes one level deep, so if further unnesting is required, a custom Matcher could be implemented. In this example, the wrapping *RuntimeException* does not alter the message, so it can be asserted directly. If the outer Exception has its own message, another custom Matcher would be needed to assert on the inner message.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/junit/ExceptionsTest.java' method='CoreMatchers.isA' %}
{% include includemethod filename='src/com/rememberjava/junit/ExceptionsTest.java' method='cause()' before=1 %}
{% endhighlight %}

Here is the full test case listing.

{% include javafile filename='src/com/rememberjava/junit/ExceptionsTest.java' %}

[Rule]: http://junit.org/junit4/javadoc/latest/org/junit/Rule.html
[ExpectedException]: http://junit.org/junit4/javadoc/latest/org/junit/rules/ExpectedException.html
[Hamcrest]: http://hamcrest.org/JavaHamcrest/
