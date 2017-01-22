---
layout: post
title:  Hello World with JavaFx
date:   2017-01-22 15:07:26 +0100
categories: javafx
---

[JavaFx][JavaFx] is a comprehensive platform and set of APIs to develop desktop, web and even mobile applications. Although intended to replace the Swing GUI API, it can get significantly more complex, with [FXML for XML based object specification][fxml]; CSS styling; and special [IDE tool chains][efxclipse].

Since JDK 8, it is supposedly included in the main install, however, on Debian I had to install it separately:

{% highlight shell %}
apt-get install openjfx openjfx-source
{% endhighlight %}


The [Oracle documentation][oracle-doc] and [tutorial][oracle-tut] is detailed and well written, so I will not go into further detail here. The following is a simple Hello World app.

{% include javafile filename='src/com/rememberjava/ui/JavaFxHelloWorld.java' %}

[JavaFx]: http://docs.oracle.com/javase/8/javafx/get-started-tutorial/jfx-overview.htm
[fxml]: http://docs.oracle.com/javafx/2/api/javafx/fxml/doc-files/introduction_to_fxml.html
[efxclipse]: http://www.eclipse.org/efxclipse/index.html
[oracle-doc]: http://docs.oracle.com/javase/8/javase-clienttechnologies.htm
[oracle-tut]: http://docs.oracle.com/javafx/2/get_started/hello_world.htm