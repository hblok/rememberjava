---
layout: post
title:  Custom include function
date:   2016-10-23 12:05:03 +0200
categories: test
---

includemethod template:

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/junit/HelloJunit.java' method='test()' before=1 %}
{% endhighlight %}

includelines template:
{% highlight java %}
{% include includelines filename='src/com/rememberjava/junit/HelloJunit.java' start=1 count=4 %}
{% endhighlight %}


{% capture file %}
    {% include src/com/rememberjava/junit/HelloJunit.java %}
{% endcapture %}

split: 
{% assign start = 9 %}
{% assign count = 4 %}
{% assign lines = file | newline_to_br | split: '<br />' %}
{% highlight java %}
    {% for line in lines offset:start limit:count %}{{ line }}{% endfor %}
{% endhighlight %}

slice:
{% highlight java %}
    {{ file | slice: 132, 57 }}
{% endhighlight %}

includelines plugin:
{% comment %}
{% highlight java %}
    {% includelines src/com/rememberjava/junit/HelloJunit.java 7 12 %}
{% endhighlight %}
{% endcomment %}
