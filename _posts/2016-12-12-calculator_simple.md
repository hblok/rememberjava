---
layout: post
title:  A simple stupid calculator  
date:   2016-12-12 20:03:03 +0100
categories: calculator
tags: calculator ui swing
---

![Calculator](/assets/rj_calc1.png)

This post includes the UI (Swing) for a very simple calculator. There's not much to say about [the code][src], except for the rather stupid way it handles the calculator operation itself: Using a JavaScript engine! The [*ScriptEngineManager*][SEM] and the internal [*NashornScriptEngineFactory*][NSEF] JavaScript implementation have been around since Java 6 and 8 receptively. It makes it easy to execute a string as snippet of code, as seen below.

Here the model for the display of the calculator is just a plain string. That string is then evaluated as a line of JavaScript, and the output is returned and put back into the "model".


{% highlight java %}
  {
    NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
    engine = factory.getScriptEngine();
  }

  ...
  String model = "1 + 1";

  void calculate() {
    try {
      Object eval = engine.eval(model);
      model = eval.toString();
    } catch (ScriptException e) {
      // If the expression was invalid,
      // don't modify the calculator display.
    }
  }
{% endhighlight %}

In terms of the calculator functionality, this implementation is very simple, but therfore also limited. A more common way of implementing this would be through some object expression representation which can be evaluated. However, the Script Engine implementation has the benefit of supporting functionality which is not even implemented in the UI, like brackets or other operators like power-to (^). It works nicely has a prototype and quick mock, so maybe not so stupid after all.

To be continued...

For the full [source of this first version, see here][src].

[src]: https://github.com/hblok/rememberjava/tree/63a8f7153f0c36fa74368cc3456d33a1ff9d2989/_includes/src/com/rememberjava/calc
[SEM]: https://docs.oracle.com/javase/8/docs/api/index.html?javax/script/ScriptEngineManager.html
[NSEF]: http://docs.oracle.com/javase/8/docs/jdk/api/nashorn/jdk/nashorn/api/scripting/NashornScriptEngineFactory.html
