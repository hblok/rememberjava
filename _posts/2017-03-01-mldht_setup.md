---
layout: post
title:  Connecting to Bittorrent's Mainline DHT
date:   2017-03-01
categories: bittorrent
tags: dht bittorrent p2p
---

In addition to its fast and efficient file transfer protocol, Bittorrent and other peer-to-peer file sharing networks bring another interesting technology to the masses: the [Distributed Hash Table (DHT)][DHT-wiki]. In the case of Bittorrent, this is used to look up and download a torrent file based on its [magnet link][magnet-wiki]. The DHT network for Bittorrent is called [Mainline DHT][Mainline-wiki] based the [Kademlia][Kademlia-wiki] DHT, although the network itself is separate from [other implementations][other-Kademlia].

For Mainline DHT, there is an interesting open source client and library, called "*mldht*". There are two instances on Github, with [moreus/mldht][moreus-mldht] as the original and the fork [the8472/mldht][the8472-mldht]. Although, both seems to be somewhat active. Both depend on the [EdDSA library][str4d-ed25519] which [the8472 has also forked][the8472-ed25519]. To get started, grab the source, and make sure the "mldht" code depend on or have access to the "ed25519" project. To confirm run the 28 unit tests from "mldht". They should all pass.

{% highlight shell %}
https://github.com/the8472/ed25519-java.git

https://github.com/the8472/mldht.git
{% endhighlight %}

The "mldht" project includes a stand-alone DHT server node which can be started through the executable [Launcher][Launcher] class. Its main configuration is in "config.xml" which gets written to the current directory if it does not already exist. It's based on "[config-defaults.xml][config-defaults]". To be able to connect to the DHT network, I had to change the option "*multihoming*" to *false* in the config file.

Furthermore, in order to use the CLI client utility, the CLI server has to be enabled. I ended up with the following configuration file.

Once started, the activity can be observed in *log/dht.log*. There will be plenty of noise.

{% highlight xml %}
<?xml version="1.0" encoding="UTF-8"?>
<mldht:config
  xmlns:mldht="http://mldht/config/"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
  xsi:schemaLocation="http://mldht/config/config.xsd ">

  <core>
    <logLevel>Info</logLevel>
    <port>49001</port>
    <useBootstrapServers>true</useBootstrapServers>
    <multihoming>false</multihoming>
    <persistID>true</persistID>
  </core>
    
  <components>
    <component>
      <className>the8472.mldht.cli.Server</className>
    </component>
  </components>

</mldht:config>
{% endhighlight %}

While the server is running, the CLI [Client][Client] executable can be used to issue a few commands. Of interest is the "SAMPLE" command which lists random hash keys from random peers. Using that output, the "GETPEERS" can be used to look up specific hashes (make sure to remove the space formatting from the sample output). Finally, a torrent file can be downloaded with the "GETTORRENT" command.

Assuming the Java classpath is set correctly, example use would look like:

{% highlight shell %}
java the8472.mldht.cli.Client SAMPLE
java the8472.mldht.cli.Client GETPEERS f61c5a0dfaac58ba943c5d0c115343477196ad91
java the8472.mldht.cli.Client GETTORRENT f61c5a0dfaac58ba943c5d0c115343477196ad91
{% endhighlight %}

The hash used above is the Wikileaks "insurance" file posted last December, with the name "2016-12-09_WL-Insurance.aes256". The "mldht" project does not contain any tools to actually read the torrent, but we can use the Transmission client:

{% highlight shell %}
apt-get install transmission-cli

transmission-show f61c5a0dfaac58ba943c5d0c115343477196ad91.torrent
{% endhighlight %}

The expected output would look like this:

{% highlight shell %}
Name: 2016-12-09_WL-Insurance.aes256
File: F61C5A0DFAAC58BA943C5D0C115343477196AD91.torrent

GENERAL

  Name: 2016-12-09_WL-Insurance.aes256
  Hash: f61c5a0dfaac58ba943c5d0c115343477196ad91
  Created by: 
  Created on: Unknown
  Piece Count: 42979
  Piece Size: 2.00 MiB
  Total Size: 90.13 GB
  Privacy: Public torrent

TRACKERS

FILES

  2016-12-09_WL-Insurance.aes256 (90.13 GB)
{% endhighlight %}


Given that this worked fine, I'm thinking it should be trivial to create a custom ML DHT client which performs the steps above. I hope to come back to that in a future article.


[DHT-wiki]: https://en.wikipedia.org/wiki/Distributed_hash_table
[Mainline-wiki]: https://en.wikipedia.org/wiki/Mainline_DHT
[magnet-wiki]: https://en.wikipedia.org/wiki/Magnet_URI_scheme
[Kademlia-wiki]: https://en.wikipedia.org/wiki/Kademlia
[other-Kademlia]: https://en.wikipedia.org/wiki/Kademlia#Implementations

[moreus-mldht]: https://github.com/moreus/mldht
[the8472-mldht]: https://github.com/the8472/mldht
[the8472-ed25519]: https://github.com/the8472/ed25519-java
[str4d-ed25519]: https://github.com/str4d/ed25519-java

[Launcher]: https://github.com/the8472/mldht/blob/master/src/the8472/mldht/Launcher.java
[config-defaults]: https://github.com/the8472/mldht/blob/master/src/the8472/mldht/config-defaults.xml
[Client]: https://github.com/the8472/mldht/blob/master/src/the8472/mldht/cli/Client.java
