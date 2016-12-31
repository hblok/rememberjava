---
layout: post
title:  sequential vs. Parallel Streams
date:   2016-12-31 08:13:32 +0100
categories: lambda parallel
published: false
---

{% highlight java %}
    {% include includemethod filename='src/com/rememberjava/lambda/ParallelCount.java' method='sequential()' before=3  after=5 %}
{% endhighlight %}

{% highlight shell %}


 --- parallelWork ---
CPU count: 4

    0000000000111111111122222222223333
    0123456789012345678901234567890123
 9:  #########                          [   6 -  909]
13:  #############                      [   6 - 1312]
 5:  #####                              [   6 -  507]
 2:  ##                                 [   6 -  207]
 3:    ###                              [ 207 -  507]
 4:       ####                          [ 507 -  909]
 1:       #                             [ 507 -  608]
 7:        #######                      [ 609 - 1312]
10:           ##########                [ 911 - 1915]
12:           ############              [ 911 - 2116]
14:               ##############        [1313 - 2719]
 6:               ######                [1313 - 1915]
 8:                     ########        [1915 - 2719]
11:                     ###########     [1915 - 3021]
    0123456789012345678901234567890123
    0000000000111111111122222222223333

 --- sequential ---
1
2
3
4
5
6
7
8
9
10
11
12
13
14

 --- parallel ---
9
12
5
4
13
7
6
11
10
1
2
14
3
8


{% endhighlight %}

{% highlight java %}
{% include src/com/rememberjava/lambda/ParallelCount.java %}
{% endhighlight %}

