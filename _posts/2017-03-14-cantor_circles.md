---
layout: post
title:  Cantor circles and recursion
date:   2017-03-14
categories: graphics
tags: recursion graphics double-buffering swing
---

This article draws inspiration from an old post dubbed "Cantor Circles", but which turned out to render bisecting circles rather than [Cantor sets][cantor-set]. Nevertheless, it gained some interest, and the exact image below, from the 2002 post, can now be easily found on [Google Image search][cantor-circles-search]. In this post, the old code is revived, and animation and colors are added for nice patterns and fun. Cantor's ternary set is also implemented.

{% include image_center src="cantor/2002_47_cantor.png" %}

I suspect the origin of the bisecting circles was a simple example for recursive code, and simply dividing by two makes the code and rendering easy to understand. In the block below, the function *drawCircles()* is recursive, calling itself twice with parameters to half the next circles. A recursive function needs a stopping condition, and in this case it is the parameter *times* which stops further recursions when it reaches 0. The helper function *drawMidCircle()* makes it more convenient to set the coordinates and size of the circle.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/graphics/Cantor.java' method='void drawCircles' %}
{% include includemethod filename='src/com/rememberjava/graphics/Cantor.java' method='void drawMidCircle' %}
{% endhighlight %}

{% include image_center src="cantor/cantor_bw.gif" %}

The next example adds animation. The recursion is the same, but adds an angle parameter to rotate the circles. Also notice that the angle *a* is negated, which leads to the alternating clockwise and counter-clockwise rotation within each small circle.

Also of interest here, is the [Swing double buffering][double-buffering] (actually, triple buffering is used in this example) using the [*BufferStrategy*][BufferStrategy] class. Notice that the *paint()* method of the Frame is no longer overridden to render the graphics, but rather the *Graphics* object is provided by the *BufferStrategy* and passed to the custom *render()* function.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/graphics/CantorSpin.java' method='void drawCircles' %}
{% endhighlight %}

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/graphics/CantorSpin.java' method='createBufferStrategy' before=0  after=10 %}
{% endhighlight %}

{% include image_center src="cantor/ui.png" %}

The last class in this article adds colors, a few options and a spartan UI to tune them. The images and animation below give a quick impression of what is possible by adjusting the sliders. Also note, the color palette can be easily changed during runtime by pasting in hex based color string found on pages like [colourlovers.com][colourlovers].


[![](/images/cantor/yellow_light_small.png)](/images/cantor/yellow_light.png)
[![](/images/cantor/yellow_mid_small.png)](/images/cantor/yellow_mid.png)
[![](/images/cantor/yellow_dark_small.png)](/images/cantor/yellow_dark.png)

![](/images/cantor/blue.gif)
![](/images/cantor/red.gif)
![](/images/cantor/cantor_set.gif)


The GIF animations were created using ImageMagick, with commands like these:

{% highlight shell %}
convert -delay 2 -loop 0 cantor*png cantor.gif

convert -delay 2 -loop 0 -resize 300x300 -layers Optimize -duplicate 1,-2-1 cantor0{0000..1200}.png cantor.gif
{% endhighlight %}


{% include javafile filename='src/com/rememberjava/graphics/Cantor.java' %}

{% include javafile filename='src/com/rememberjava/graphics/CantorSpin.java' %}

{% include javafile filename='src/com/rememberjava/graphics/CantorColors.java' %}


[cantor-set]: https://en.wikipedia.org/wiki/Cantor_set
[cantor-circles-search]: https://www.google.com/search?q=cantor+circles&tbm=isch
[double-buffering]: https://docs.oracle.com/javase/tutorial/extra/fullscreen/doublebuf.html
[BufferStrategy]: https://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferStrategy.html
[colourlovers]: http://www.colourlovers.com/palette/4464838/sea
