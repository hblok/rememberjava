---
layout: post
title:  TrayIcon Example
date:   2016-12-17 15:30:54 +0100
categories: UI trayicon
---

![Tray Icon](/assets/trayicon.png)

Since Java 6, adding a system tray icon has been straight forward. The two main classes involved are [SystemTray][ST] and [TrayIcon][TI]. Various OS might render and operate the icon differently. Typically, there is a status message on hover, a short-cut action on left click, and possibly a menu on right click. The TrayIcon supports all this. In the example code below, an 16x16 pixel PNG is used, and auto-scaled up to about 24 pixels, which is what I've configured by XFCE panel.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/ui/TestTrayIcon.java' method='void initIcon()' before=0  after=0 %}
{% endhighlight %}

Here's the full example class listing.

{% include javafile filename='src/com/rememberjava/ui/TestTrayIcon.java' %}

[ST]: https://docs.oracle.com/javase/8/docs/api/java/awt/SystemTray.html
[TI]: https://docs.oracle.com/javase/8/docs/api/java/awt/TrayIcon.html



