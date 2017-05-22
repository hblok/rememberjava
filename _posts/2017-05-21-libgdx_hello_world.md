---
published: false

layout: post
title:  Hello world with LibGDX
date:   2017-05-21
categories: graphics
tags: libgdx lwjgl graphics games
---

[libGDX][libGDX] is a cross-platform Java game development library. It supports Windows, Gnu/Linux, Android, iOS, Mac, and web. The desktop variations use the [Lightweight Java Game Library (LWJGL)][lwjgl], with OpenGL support. There are already [good comprehensive tutorials][gamefromscratch] out there, so this article will only present the "Hello World" example with a small animation.

As opposed to basic OpenGL libraries, LibGDX and LWJGL offer a complete framework for creating a game, animations or interactions. To lower the bar to entry, the *ApplicationListener* interface has the common methods, most important the *render()* method, which is automatically called in a loop.

In the example below, a rotation matrix is defined and set, followed by drawing of the words "Hello World". Because this method is called in a loop, the *angle* variable will update, and thus set a new rotation matrix every time. Similarly, the color is updated for each frame. The effect is a continuous never-ending animation.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/graphics/LibGdxHelloWold.java' method='render()' before=1 after=0 %}
{% endhighlight %}

{% include image_center src="libgdx_hello_world.png" %}

[There are multiple GDX packages][mvnrepository], for different platforms and features, and for this example the following three are needed from the Maven Central repository. Be sure to include the **natives-desktop** version annotation on the gdx-platform package. For other platforms, e.g. Android, other packages are needed.

{% highlight shell %}
repositories {
  mavenCentral()
}

dependencies {
  compile 'com.badlogicgames.gdx:gdx:1.9.6'
  compile 'com.badlogicgames.gdx:gdx-platform:1.9.6:natives-desktop'
  compile 'com.badlogicgames.gdx:gdx-backend-lwjgl:1.9.6'
}
{% endhighlight %}

Here is the full code, as a stand-alone application.

{% include javafile filename='src/com/rememberjava/graphics/LibGdxHelloWold.java' %}

[libGDX]: http://libgdx.badlogicgames.com/
[lwjgl]:  https://www.lwjgl.org/
[gamefromscratch]: http://www.gamefromscratch.com/page/LibGDX-Tutorial-series.aspx
[mvnrepository]: https://mvnrepository.com/artifact/com.badlogicgames.gdx