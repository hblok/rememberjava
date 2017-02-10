---
layout: post
title:  Sequential vs. Parallel Streams
date:   2016-12-31 08:13:32 +0100
categories: lambda parallel
tags: lambda parallel
---

The [Java 8 Streams][STREAMS] API offers functional-style operations, and a simple way to execute such operations in parallel. To jump right into an example, the following two test methods show the difference between sequential and parallel execution of the the *println()* method over each of the elements of the stream.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/lambda/ParallelCount.java' method='sequential()' before=4  after=5 %}
{% endhighlight %}

As is expected, the default sequential execution will print the elements of the stream, which in this example are integers from 1 to 14, in natural order. By using the [*parallel()*][PARALLEL] method to create a parallel stream and call the same print method, the only noticeable difference is that they are printed out of order. 

{% highlight shell %}
 --- sequential ---      --- parallel ---
1                       9
2                       12
3                       5
4                       4
5                       13
6                       7
7                       6
8                       11
9                       10
10                      1
11                      2
12                      14
13                      3
14                      8
{% endhighlight %}

To see how the parallel stream behaves, the last test applies a different method to each of the elements of the stream. In this example, the time spent by the *work()* method on each element is linearly proportional to its value. That is, for the element of value 1 it spends 100ms, for 2 its 200 ms and so on. The "work" it does, is simply to sleep for intervals of 100 ms, and the rest of the code is dedicated to printing and formatting the table below. However, it serves the purpose of demonstrating how parallel execution behaves, and how it relates to the underlying CPU(s).

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/lambda/ParallelCount.java' method='this::work' before=0  after=0 %}
{% endhighlight %}

In the result table, each element of the stream is represented by a row. Each row shows the value of the element, the time-slots the *work()* method was executing, and to the right the time in milliseconds when work started and finished. Each 100 ms slot is represented by a hash. As can be seen, the first row was element 9, thus it marked off nine slots, and the starting time was at 6 ms, and finish at 909 ms.

Furthermore, since this was run on a machine with four CPU cores, the stream will execute four calls in parallel. This can be seen by both the hashes and the start times of the first four rows. Next, when element 2 (fourth row) finishes at 207 ms, a new element is immediately started (element 3, fifth row).

In this example, the total number of 100 ms "units of work" can be found by the formula for the [triangular number][TRINUM] where n = 14, or 14 * (14 + 1) / 2 = 105. Meaning that, sequential execution would have taken 10.5 seconds, while four parallel CPUs managed in 3 seconds.

In the second table below, the same code is executed on a dual core CPU, and it is clear that now only two methods execute in parallel. That will of course lead to a longer overall runtime, of about 5.4 seconds for this example. This could lead to a discussion on task and scheduling optimisation, however it goes beyond this article, and what is possible with the simple parallel Stream construct.

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
{% endhighlight %}

{% highlight shell %}
 --- parallelWork ---
CPU count: 2

    00000000001111111111222222222233333333334444444444555555555
    01234567890123456789012345678901234567890123456789012345678
 8:  ########                                                    [  23 -  825]
 4:  ####                                                        [  23 -  424]
 5:      #####                                                   [ 424 -  926]
 9:          #########                                           [ 826 - 1727]
 6:           ######                                             [ 926 - 1527]
 7:                 #######                                      [1527 - 2228]
10:                   ##########                                 [1727 - 2729]
 1:                        #                                     [2229 - 2329]
 2:                         ##                                   [2329 - 2529]
 3:                           ###                                [2530 - 2830]
13:                             #############                    [2729 - 4031]
11:                              ###########                     [2831 - 3932]
12:                                         ############         [3933 - 5134]
14:                                          ##############      [4031 - 5433]
    01234567890123456789012345678901234567890123456789012345678
    00000000001111111111222222222233333333334444444444555555555

{% endhighlight %}

The full code list is here.

{% include javafile filename='src/com/rememberjava/lambda/ParallelCount.java' %}


[STREAMS]: https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html
[PARALLEL]: https://docs.oracle.com/javase/8/docs/api/java/util/stream/BaseStream.html#parallel--
[TRINUM]: https://en.wikipedia.org/wiki/Triangular_number
