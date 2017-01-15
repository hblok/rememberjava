---
layout: post
title:  Send and Receive money with bitcoinj
date:   2017-01-15 12:37:40 +0100
categories: bitcoin
---

The [bitcoinj][bitcoinj] library is easy to use for Bitcoin wallet and transaction functions for both native Java and Android applications. Although there are certain features missing, it seems mature enough to be included in a Bitcoin walled app or service.

Sometimes the source code leaves a bit to be desired in structure and readability: anonymous inner classes and other deep nesting blocks sometimes makes it difficult to follow; inheritance is often used where composition would have been be better; the Collections classes could have been used over arrays in many places. All of this might come back to haunt the developers later, for now they seem to be plowing on.

At least the basics are straight forward. The following code will read a test walled from disk, or create a new one if it does not already exist. The [TestNet3][tn3] block chain and network is used. Since the bitcoinj library relies heavily on the Google Guava (com.google.common) classes, there are frequent artifacts of the threading and callback handling showing up. In this example, we want the code to block and wait, therefore the extra *await*-functions are required.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/bitcoin/BitcoinjApiTest.java' method='testSync()' before=0  after=0 %}
{% endhighlight %}

The nice thing about the test block chain is that it is a real public live block chain, with miners and a [block chain explorer][explorer], but with no value in the coins. In fact, you can get free coins to test with from [faucet.xeno-genesis.com][fxg] or [tpfaucet.appspot.com][tpf]. (The latter has been timing out over the last days).

To get some free test coins, run the following code, wait for the prompt which shows the next receiving address, and head over to [faucet.xeno-genesis.com][fxg] to ask them to send some money there. It should show up as received within a few seconds. Your wallet now contains some coins.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/bitcoin/BitcoinjApiTest.java' method='testReceive()' before=0  after=0 %}{% include includemethod filename='src/com/rememberjava/bitcoin/BitcoinjApiTest.java' method='void coinsReceived' before=0  after=0 %}
{% endhighlight %}

Since the test network is a real network with real miners, it's good etiquette to return your test coins to the pool for others to use once you're done with them. The following code takes care of that, returning them to the TP Faucet default return address *"n2eMqTT929pb1RDNuqEnxdaLau1rxy3efi"*. You can return all your coins, or just a fraction if you want to experiment more. This will also wait a few seconds for the callback confirmation.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/bitcoin/BitcoinjApiTest.java' method='testSend()' before=0  after=0 %}
{% endhighlight %}

Finally, it's worth noting that bitcoinj is a "live" library, in development and with the latest update available through [Gradle][gradle]. To make this work, there's a few settings and dependencies to take care of. The logging framework used by bitcoinj is [SL4J][sl4j], and an actual implementation library (e.g. "sl4j-simple") is also need. It can be [downloaded][sl4j-down], or included as a Gradle build dependency as seen below.

Then, your source code directory structure might not match the default Gradle "main", "test" structure. My current structure keeps all source code under the directory "src", so I have specified that.

Gradle integrates OK with Eclipse, but be careful with the "refresh" option. It tends to insist on changing the classpath setting of the project, so the packages disappear. It's a good idea to keep the *.classpath* setting file under version control.

{% highlight shell %}
{% include src/build.gradle %}
{% endhighlight %}

The following listing shows all the tests. It demonstrates similar functionality as seen in the [ForwardingService][fs-tut] class in the main [bitcoinj getting started guide][bitcoinj-start]. Hopefully, the code is a bit easier to read and run this way.

{% highlight java %}
{% include src/com/rememberjava/bitcoin/BitcoinjApiTest.java %}
{% endhighlight %}


[bitcoinj]: https://bitcoinj.github.io
[tn3]: https://en.bitcoin.it/wiki/Testnet
[explorer]: https://testnet.blockexplorer.com
[fxg]: http://faucet.xeno-genesis.com
[tpf]: http://tpfaucet.appspot.com
[gradle]: https://gradle.org
[sl4j]: https://www.slf4j.org
[sl4j-down]: https://www.slf4j.org/download.html
[fs-tut]: https://github.com/bitcoinj/bitcoinj/blob/master/examples/src/main/java/org/bitcoinj/examples/ForwardingService.java
[bitcoinj-start]: https://bitcoinj.github.io/getting-started-java
