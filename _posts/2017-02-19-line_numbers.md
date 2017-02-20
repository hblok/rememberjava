---
layout: post
title:  Line numbers for JEditorPane and JTextPane
date:   2017-02-19
categories: ui
tags: ui swing
---

There are many ways to add line numbers to the [*JEditorPane*][JEditorPane] and [*JTextPane*][JTextPane] Swing components. This post looks at two solutions: One providing a custom [*ParagraphView*][ParagraphView] which paints a child element that contains the line number for that paragraph. The other implements a separate component which is passed as the RowHeaderView of the [*JScrollPane*][JScrollPane]. Both support scrolling and line wrapping, and in this example the latter includes current line highlighting, as seen in the image below.

![Line numbers with RowHeaderView](/images/line_numbers.png)

<h2>ParagraphView</h2>
The ParagraphView solution is inspired by [Stanislav Lapitsky's old post][Lapitsky] on the topic, although with some improvements. The basic idea is to paint the line number in the child of the ParagraphView, using the *paintChild()* method. The advantage is that the relationship between the line and the line number is already established, so alignment is easy. We just have to count which element we're dealing with to know which number to print. The two methods below show this part. Notice also that only the index 0 of the ParagraphView should be used for the line number, so we don't count a wrapped line twice. Furthermore, a mono-spaced font helps with padding and alignment.

In order to create this custom ParagraphView, it has to be returned through a *ViewFactory*, which again has to be provided through an *EditorKit*. The file below shows the full implementation. Notice how the default *ViewFactory* is used for all other elements; only the Paragraph Element gets a custom view.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/ui/ParagraphViewLineNumbers.java' method='void paintChild' %}
{% include includemethod filename='src/com/rememberjava/ui/ParagraphViewLineNumbers.java' method='int getLineNumber()' %}
{% endhighlight %}

<h2>setRowHeaderView</h2>
The second solution renders the line numbers in a completely separate component, which can be a JComponent (or even an old style AWT Component). This component is passed in to the [*setRowHeaderView()* method of JScrollPane][setRowHeaderView]. That way, the coupling becomes a bit cleaner than overriding multiple classes and methods as with the the ParagraphView solution. However, the down-side is that we must do the alignment with the Editor component manually. Luckily, the Swing Text Component API provides a solid API for this purpose.

The code below is inspired by [Rob Camick's article][Camick], but simplifies several aspects of his stand-alone API style class. At the centre is the translation between the [*Document* model][Document] and its onscreen view. The [*viewToModel()*][viewToModel] method translates an x,y point on the screen to the nearest character offset, while the [*modelToView()*][modelToView] method goes the other way. That way we can align the line number at the same y point as the line in the editor. In Camick's code, he loops through the text by character offsets rather than paragraphs; the [*getRowEnd()*][getRowEnd] method helps by finding the end of a line. Camick also points out the the line number should take the font decent of the editor font into account when positioning the line number. The *FontMetrics* class helps with measuring sizes of fonts.

Finally worth noting is the synchronisation between the editor and the margin component. Since they are separate, this must be handled through event listeners registered with the editor component. The *DocumentListener* and *ComponentListener* notifies of edit, movement and resizing events, while the *CaretListener* signals movement in the cursor position. All of these events force a repaint, but through a scheduled AWT thread, to make sure the editor view has updated first, as seen in the *documentChanged()* method at the bottom of the code section below.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/ui/RowHeaderViewLineNumbers.java' method='void paintComponent' %}
{% include includemethod filename='src/com/rememberjava/ui/RowHeaderViewLineNumbers.java' method='String getLineNumber' %}
{% include includemethod filename='src/com/rememberjava/ui/RowHeaderViewLineNumbers.java' method='int getOffsetY' %}
{% include includemethod filename='src/com/rememberjava/ui/RowHeaderViewLineNumbers.java' method='boolean isCurrentLine' %}
{% include includemethod filename='src/com/rememberjava/ui/RowHeaderViewLineNumbers.java' method='void documentChanged' %}
{% endhighlight %}

Here are the full code listings for both examples, as stand-alone applications.

{% include javafile filename='src/com/rememberjava/ui/ParagraphViewLineNumbers.java' %}

{% include javafile filename='src/com/rememberjava/ui/RowHeaderViewLineNumbers.java' %}

[JEditorPane]: https://docs.oracle.com/javase/8/docs/api/index.html?javax/swing/JEditorPane.html
[JTextPane]: https://docs.oracle.com/javase/8/docs/api/index.html?javax/swing/JTextPane.html
[JScrollPane]: https://docs.oracle.com/javase/8/docs/api/index.html?javax/swing/JScrollPane.html
[ParagraphView]: https://docs.oracle.com/javase/8/docs/api/index.html?javax/swing/text/ParagraphView.html
[Document]: https://docs.oracle.com/javase/8/docs/api/javax/swing/text/Document.html
[viewToModel]: https://docs.oracle.com/javase/8/docs/api/javax/swing/text/JTextComponent.html#viewToModel-java.awt.Point-
[modelToView]: https://docs.oracle.com/javase/8/docs/api/javax/swing/text/JTextComponent.html#modelToView-int-
[setRowHeaderView]: https://docs.oracle.com/javase/8/docs/api/javax/swing/JScrollPane.html#setRowHeaderView-java.awt.Component-
[getRowEnd]: https://docs.oracle.com/javase/8/docs/api/javax/swing/text/Utilities.html#getRowEnd-javax.swing.text.JTextComponent-int-

[Lapitsky]: http://www.developer.com/java/other/article.php/3318421
[Camick]: https://tips4java.wordpress.com/2009/05/23/text-component-line-number
