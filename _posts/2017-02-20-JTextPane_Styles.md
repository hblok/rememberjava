---
layout: post
title:  Styles with JTextPane
date:   2017-02-20
categories: ui 
tags: ui regexp swing
---

The [Swing tutorial for the JTextPane][swing-tutorial] and [demo code][demo-code] is good and comprehensive, covering multiple ways to interact with the the *StyledDocument* and add *Styles*. So, without repeating all of that, here's a minimal example including only simple style changes on the insert update and selection events.

In the first snippet below, the implementation for the *insertUpdate* event is shown. For each typed character or pasted text, the entire text of the document will be searched for the word "foo". For each occurrence, the bold attribute is set for that word. Notice that the update of the attribute happens on a separate AWT thread.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/ui/JTextPaneStylesExample.java' method='void insertUpdate' before=1 %}
{% include includemethod filename='src/com/rememberjava/ui/JTextPaneStylesExample.java' method='void updateAttribute' %}
{% endhighlight %}

The other functionality of this small application is on select. If the selected word is "bar", it is set to italic. Notice the *dot* and *mark* positions, which might be at the start or the end of the selection. Furthermore, notice that here the predefined [*ItalicAction*][ItalicAction] from the *StyledEditorKit* is used, since we're dealing with a selection. We just have to translate the *CaretEvent* into an *ActionEvent*, or rather just make sure to forward the source component of the selection. (The alternative would have been to go with the plain update as in the example above).

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/ui/JTextPaneStylesExample.java' method='void caretUpdate' before=1 %}
{% endhighlight %}

Here's the full file listing, as a stand-alone application.

{% include javafile filename='src/com/rememberjava/ui/JTextPaneStylesExample.java' %}

[swing-tutorial]: http://docs.oracle.com/javase/tutorial/uiswing/components/generaltext.html
[demo-code]: http://docs.oracle.com/javase/tutorial/uiswing/examples/components/TextComponentDemoProject/src/components/TextComponentDemo.java
[ItalicAction]: https://docs.oracle.com/javase/8/docs/api/index.html?javax/swing/text/StyledEditorKit.ItalicAction.html

