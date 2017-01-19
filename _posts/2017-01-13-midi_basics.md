---
layout: post
title:  MIDI basics
date:   2017-01-13 19:58:04 +0100
categories: midi
---

Working with MIDI in Java is easy. The standard API have classes covering MIDI file I/O, device I/O, sequencing and sound synthesis. [This comprhensive tutorial][midi-tut] covers most aspects. It also helps to know something about the MIDI format. [Juan Bello's slide deck][bello] is an excellent introduction. [midi.org][midi-org] is also a good source, including their full [message reference table][msgref].

To play a single C note through the default included "Gervill" soft synthezier, the following snippet will do. As commented in the code, there's come discrepancy on pitch or octave labelling, but the 60th note is still on the save octave as A at 440 Hz.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/midi/MidiApiTest.java' method='testPlayNote()' before=0  after=0 %}
{% endhighlight %}

This gives a brief overview of the MIDI devices on the system, both software based and hardware devices. Depending on drivers and OS, the various devices might show up under different names and types.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/midi/MidiApiTest.java' method='testGetMidiDeviceInfo()' before=0  after=0 %}
{% endhighlight %}

Finally, the following methods demonstream MIDI file I/O. 

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/midi/MidiApiTest.java' method='testWriteFile()' before=0  after=0 %}
{% include includemethod filename='src/com/rememberjava/midi/MidiApiTest.java' method='testReadFile()' before=0  after=0 %}
{% include includemethod filename='src/com/rememberjava/midi/MidiApiTest.java' method='testMidiFileFormat()' before=0  after=0 %}
{% endhighlight %}

The full test case can be seen below. There's a few more helper classes and details [in the same package here][rj-midi]. Then there's some special implementation and details for the [Roland TB-03 and TR-8 devices here][rj-roland].

{% include javafile filename='src/com/rememberjava/midi/MidiApiTest.java' %}

[midi-tut]: https://docs.oracle.com/javase/tutorial/sound/overview-MIDI.html
[bello]: https://www.nyu.edu/classes/bello/FMT_files/9_MIDI_code.pdf
[midi-org]: http://midi.org
[msgref]: https://www.midi.org/specifications/item/table-1-summary-of-midi-message
[rj-midi]: https://github.com/hblok/rememberjava/tree/master/_includes/src/com/rememberjava/midi
[rj-roland]: https://github.com/hblok/rememberjava/tree/master/_includes/src/com/rememberjava/midi/roland

