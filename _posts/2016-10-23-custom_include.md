---
layout: post
title:  Custom include function
date:   2016-10-23 12:05:03 +0200
categories: test
---
This is a test

{% capture file %}
    {% include src/com/rememberjava/junit/HelloJunit.java %}
{% endcapture %}

split: 
{% assign lines = file | newline_to_br | split: '<br />' %}
{% assign start = 9 %}
{% assign count = 4 %}
{% highlight java %}
    {% for line in lines offset:start limit:count %}{{ line }}{% endfor %}
{% endhighlight %}

slice:
{% highlight java %}
    {{ file | slice: 132, 57 }}
{% endhighlight %}

includelines: 
{% highlight java %}
    {% includelines src/com/rememberjava/junit/HelloJunit.java 7 12 %}
{% endhighlight %}


