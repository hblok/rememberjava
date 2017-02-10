---
layout: post
title:  "Hello Junit Test!"
date:   2016-10-08 14:00:00 +0200
categories: basics junit
tags: junit basics
---

Unit testing can sometimes be like washing the dishes: You know it should be done, that you'll have to do it eventually, but it can always be postponed. On the other hand, executing your code through unit tests is a great way to verify that it works as expected, in a short amount of time and with little code overhead. Furthermore, writing unit tests is a convenient way to execute any kind of code easily, especially from within a modern IDE, where it is typically easier to run a single test method than a full executable. Many of the examples on this site will use unit tests as the driver.

Test Method
====
The "Hello World" minimal example of a Junit 4 test method can be seen in the first example below. All it takes, is a method (of any name) annotated with the *@Test* annotation. Typically, a test method will assert something against expected values, albeit in the examples below this is ineffectual.

{% highlight java %}
{% include src/com/rememberjava/junit/HelloJunit.java %}
{% endhighlight %}

Setup and teardown
===
The anatomy of a test case class is as seen in the following example. Again, the method names are not relevant, and only the annotations indicate at what point they will be executed. All the annotations can appear on multiple methods, and the only additional requirements are that all annotated methods have to be public; and that the before and after class methods have to be static.

{% highlight java %}
{% include src/com/rememberjava/junit/JunitTestMethods.java %}
{% endhighlight %}

Order of execution
===
The final example below adds some flesh to the body of each of the methods, and illustrates a few basic principles of how the different methods are used.

* The annotations indicate the order: BeforeClass, Before, Test, After, AfterClass.
* BeforeClass and AfterClass methods are run once, while Before and After methods are executed for every Test method.
* Since the Class level methods are static, any global fields they use also have to be static.
* The class is re-constructed for every single Test method. This will reset all global fields, as seen in the output below.
* The order between Test methods is typically chronological as they appear in the file, however, it is good practice not to rely on this. Or rather, Test methods should not depend on each other.

{% highlight java %}
{% include src/com/rememberjava/junit/JunitTestMethodsOrder.java %}
{% endhighlight %}

The test case above gives the following output. Notice the static *a* counter, and the global non-static *b* counter which is reset.

{% highlight sh %}
1 beforeClass
2 setup 1
3 first 2
4 tearDown 3
5 setup 1
6 second 2
7 tearDown 3
8 afterClass
{% endhighlight %}
