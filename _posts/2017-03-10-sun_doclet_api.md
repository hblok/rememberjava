---
layout: post
title:  Sun Doclet API
date:   2017-03-10
categories: doclet
tags: doclet
---

Since Java 1.2, the *javadoc* command has generated neatly formatted documentation. The tool comes with its [own API][doclet-api] which allows [customised output][doclet-overview]. The relevant classes are under the *com.sun* package hierarchy, and located in *JRE_HOME/lib/tools.jar*, which typically will have to be included manually. E.g. it can be found under */usr/lib/jvm/java-8-openjdk-amd64/lib/tools.jar*.

Note that the Sun Doclet API is getting long in th tooth, and is already slated for replacement in Java 9, through the "Simplified Doclet API" in [JDK Enhancement Proposal 221][jep-221]. Java 9 is planned for Q3 2017.

Meanwhile, the old Doclet API still does an OK job of parsing JavaDoc in .java source files. If the goal is to parse, rather than to produce the standard formatted JavaDoc, it's useful to [start the process pragmatically][doclet-start]. Than can be achieved through its main class, *com.sun.tools.javadoc.Main*:

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/doc/SunDocletPrinter.java' method='Main.execute(' before=0  after=1 %}
{% endhighlight %}

The *execute()* method will invoke the public static method *start()* in the specified class. In the example below, a few of the main JavaDoc entities are enumerated. The direct output can be see the block below. The class which is parsed is the example class itself, included at the bottom of this article.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/doc/SunDocletPrinter.java' method='start(' before=0  after=0 %}
{% endhighlight %}


<pre><code>
- start main
Loading source file com/rememberjava/doc/SunDocletPrinter.java...
Constructing Javadoc information...
--- start
Class: com.rememberjava.doc.SunDocletPrinter
  Annotation: @java.lang.Deprecated
  Class tag:@author=Bob
  Class tag:@since=123
  Class tag:@custom=Custom Annotation
  Class tag:@see="http://docs.oracle.com/javase/6/docs/technotes/guides/javadoc/doclet/overview.html"
  Field: com.rememberjava.doc.SunDocletPrinter.SOME_FIELD
  Method: com.rememberjava.doc.SunDocletPrinter.main(java.lang.String[])
    Annotation: @java.lang.SuppressWarnings("Test")
    Doc: 
    Raw doc:
 @see "http://docs.oracle.com/javase/7/docs/technotes/guides/javadoc/standard-doclet.html"

  Method: com.rememberjava.doc.SunDocletPrinter.start(com.sun.javadoc.RootDoc)
    Doc: This method processes everything. And there's more to it.
    Tag: Text:This method processes everything.
    Param:root=the root element
    Raw doc:
 This method processes everything. And there's more to it.
 
 @param root
          the root element
 @return returns true

--- the end
- done execute

</code></pre>

Here is full file, which also shows the JavaDoc the example operates on.

{% include javafile filename='src/com/rememberjava/doc/SunDocletPrinter.java' %}


[doclet-overview]: http://docs.oracle.com/javase/6/docs/technotes/guides/javadoc/doclet/overview.html
[doclet-start]: http://docs.oracle.com/javase/7/docs/technotes/guides/javadoc/standard-doclet.html
[doclet-api]: http://docs.oracle.com/javase/8/docs/jdk/api/javadoc/doclet/index.html
[jep-221]: http://openjdk.java.net/jeps/221
