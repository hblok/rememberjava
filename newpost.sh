#!/bin/sh

day=`date +"%Y-%m-%d"`
time=`date +"%Y-%m-%d %H:%M:%S %z"`
file="_posts/${day}-${1}.md"

java=$2
java=`echo $java | sed "s@_includes/@@"`
if [[ "${java}" != *"src/com/rememberjava/"* ]]; then
  java="src/com/rememberjava/${java}"
fi

cat > $file << EOF
---
published: false

layout: post
title:  
date:   ${day}
categories: 
tags: 
---

{% highlight java %}
{% include includemethod filename='${java}' method='()' before=0  after=0 %}
{% endhighlight %}


{% include javafile filename='${java}' %}

EOF

ls $file

