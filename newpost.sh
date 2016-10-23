#!/bin/sh

day=`date +"%Y-%m-%d"`
time=`date +"%Y-%m-%d %H:%M:%S %z"`
file="_posts/${day}-${1}.md"

cat > $file << EOF
---
layout: post
title:  
date:   ${time}
categories: 
---


{% highlight java %}
{% include src/com/rememberjava/.java %}
{% endhighlight %}

EOF

