---
layout: post
title:  Terminal and ncurses Java libraries
date:   2017-01-22 12:40:29 +0100
categories: cli
tags: cli gradle
---

There's a few Java libraries for creating terminal and [ncurses][ncurses] like applications. They abstract away the [ANSI][ansi] escape [codes][terminalcodes] and low level control like [tput][tput]. I've briefly looked at three of them: [Lanterna][lanterna-gh], [Charva][charva] and [JCurses][JCurses]. They all come with basic terminal control, and some widgets and window managing to create terminal based GUI applications.

[JCurses][JCurses] seems to be the oldest, and possibly unmaintained. It requires ncurses to be installed, and also a native library. It is available through the Maven Central repository, however, make sure to set up the path for access to the *libjcurses.so* (in the tar-ball from SourceForge, or within the .jar from Maven). Documentation is sparse, but it seems it's possible to interface with the terminal with little code.

[Charva][Charva] is also ncurses based and requires a native lib. One of it's selling points is that it can act as a [drop-in replacement for java.awt UIs][charva-awt]. It seems unlikely that that will work well for all but the simplest UIs, however, at least it offers an easy transition path.

Finally, [Lanterna][lanterna-gh] seems to be the best maintained, and clearly documented project of the three. It is unique in that is handles all terminal details itself, and does not require external libraries. It is compatible with Linux, Windows, Cygwin, OS X terminals. Furthermore, if launched from an X or similar GUI based environment instead of a terminal, it will create its own GUI window into which it will render the terminal. Neat.

The drawback with Lanterna is its rather verbose and involved API. For "serious" apps, maybe that's OK, but for simple positioning of the cursor, it can get a bit too much. The follow renders a text label with a border:

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/ui/LanternaCliTest.java' method='void testWindow()' before=0  after=0 %}
{% endhighlight %}

Lanterna is [available through Maven Central][lanterna-maven], so the following *gradle.build* lines will do.

{% highlight shell %}
repositories {
  mavenCentral()
}

dependencies {
  compile 'com.googlecode.lanterna:lanterna:3.0.0-beta3'
}
{% endhighlight %}

Here's the full test example, including a main-method to easily run it stand-alone from the terminal.

{% include javafile filename='src/com/rememberjava/ui/LanternaCliTest.java' %}

[ansi]: https://en.wikipedia.org/wiki/ANSI_escape_code
[terminalcodes]: http://wiki.bash-hackers.org/scripting/terminalcodes
[tput]: https://gist.github.com/komasaru/789216cf9cf5e1aa339c
[ncurses]: https://www.gnu.org/software/ncurses/ncurses.html
[lanterna-gh]: https://github.com/mabe02/lanterna
[lanterna-maven]: http://search.maven.org/#search|ga|1|a%3A%22lanterna%22
[charva]: http://www.pitman.co.za/projects/charva/index.html
[charva-awt]: http://www.pitman.co.za/projects/charva/Screenshots.html
[JCurses]: https://sourceforge.net/projects/javacurses
