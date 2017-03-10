---
published: false

layout: post
title:  
date:   2017-03-10
categories: 
tags: 
---

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/doc/SunDocletPrinter.java' method='()' before=0  after=0 %}
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

{% include javafile filename='src/com/rememberjava/doc/SunDocletPrinter.java' %}


http://docs.oracle.com/javase/6/docs/technotes/guides/javadoc/doclet/overview.html
http://docs.oracle.com/javase/7/docs/technotes/guides/javadoc/standard-doclet.html
http://docs.oracle.com/javase/8/docs/jdk/api/javadoc/doclet/index.html

Simplified Doclet API
    http://openjdk.java.net/jeps/221
