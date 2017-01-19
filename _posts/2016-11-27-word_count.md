---
layout: post
title:  Word count
date:   2016-11-26 22:22:37 +0100
categories: lambda streams
---

Here's another neat streams snippet, which in three lines handles what would take at least two loops in pre-Java 8 code. The task at hand is to count the number of occurrences of each unique word in a file. Given the content shown below, this Bash command line would solve it.

{% highlight shell %}
cat words | tr ' ' '\n' | sort | uniq -c
{% endhighlight %}

File content:
{% highlight shell %}
{% include src/com/rememberjava/lambda/words %}
{% endhighlight %}

Shell output:
{% highlight shell %}
  4 four
  1 one
  3 three
  2 two
{% endhighlight %}

The Java code follows a similar flow as seen in the pipes above: Read the file, split each line by space, and flatten the result from all lines into a single stream. Finally, the *collect()* function is used with the *groupingBy()* helper, to map each token (or word) in the stream (the identity) to its count.

{% highlight java %}{% include includemethod filename='src/com/rememberjava/lambda/WordCount.java' method='countWords()' before=-3  after=-2 %}
{% endhighlight %}

The Java map will contain the following key-value pairs. Here, the words are also accidentally sorted alphabetically. However, the order is not guaranteed by *collect()* function, since it returns a *HashMap*.
{% highlight txt %}
  {four=4, one=1, three=3, two=2}
{% endhighlight %}

The full listing:

{% include javafile filename='src/com/rememberjava/lambda/WordCount.java' %}

