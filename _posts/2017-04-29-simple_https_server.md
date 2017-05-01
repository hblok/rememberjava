---
layout: post
title:  TLS 1.2 over SUN HttpsServer
date:   2017-04-29
categories: http
tags: http https server tls ssl
---

Security can be tricky, HTTPS and TLS no less so. There are many configuration details to be aware of, and the encryption algorithms and cipher suites are moving targets, with new vulnerabilities and fixes all the time. This example does not go into all these details, but instead shows a basic example of how to bring up a HTTPS server using a self-signed TLS 1.2 key and certificate.

The main component of Java TLS communication is the [Java Secure Socket Extension (JSSE)][jsse]. A number of [algorithms][jdk8-crypto-algo] and [cryptographic providers][jdk8-crypto-providers] are supported. The central class is the [*SSLContext*][SSLContext], supported by the [*KeyStore*][KeyStore]. These classes load and initialise the relevant keys, certificates, and protocols which are later used by a HTTPS server (or client). See the Oracle blog, for another brief [introduction to TLS 1.2][oracle-blog-tls-default], and [tips on diagnosing][oracle-blog-tls-diagnosing] the communication. In particular, notice the unlimited strength implementations, which have to be [downloaded separately][jdk8-unlimited], and copied to *JAVA_HOME/lib/security*.

The example below shows how the certificate and key are loaded from a Java KeyStore (.jks) file, and used to initialise the *SSLContext* with the TLS protocol. The *SSLContext* is passed to the SUN *HttpsServer* through a *HttpsConfigurator*. The *HttpsServer* implementation takes care of the rest, and the static file handler is the same as seen in the [plain HTTP based example][http-example].

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/http/SimpleHttpsServer.java' method='void start()' %}
{% include includemethod filename='src/com/rememberjava/http/SimpleHttpsServer.java' method='SSLContext getSslContext()' %}
{% endhighlight %}

The code also includes a hard-coded generation of the key and certificate. A normal server would of course not implement this, but it's included here to make the example self-contained and working out of the box. The following command is executed, and a Java KeyStore file containing a RSA based 2048 bits key, valid for one year. The keytool command will either prompt for name and organisational details, or these can be passed in using the *dname* argument. Also notice that password to the keystore and key are different. Further [usage details on *keytool* can be found here][keytool].

{% highlight shell %}
keytool -genkey -keyalg RSA -alias some_alias -validity 365 -keysize 2048  \
  -dname cn=John_Doe,ou=TestOrgUnit,o=TestOrg,c=US -keystore /tmp/test.jks -storepass pass_store -keypass pass_key
{% endhighlight %}

Since the certificate is self-signed, a modern browser will yield a warning, and not allow the communication to continue without an explicit exception, as seen below. For the sake of this example, that is fine. If we do allow the certificate to be used, we will see that the communication is indeed encrypted, "using a strong protocol (TLS 1.2), a strong key exchange (ECDHE_RSA with P-256), and a strong cipher (AES_128_GCM)", aka "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256".

On the topic of keys and algorithms, Elliptic Curve Cryptography (ECC) is relevant. [Digicert][ecc-intro] gives a brief introduction, with details on how to [generate keys for Apache][ecc-apache]. Openssl has further [information on EC using openssl][ecc-openssl].

{% include image_center src="firefox_not_secure.png" %}
{% include image_center src="firefox_security_info.png" %}
{% include image_center src="chrome_security_overview.png" %}

The two files below is all that is needed. A key and certificate is generate if not already present under */tmp/test.jks*. Go to *https://localhost:9999/secure/test.txt* and enable the security exception to try it out. Also notice the logging in the console window of the server.

{% include javafile filename='src/com/rememberjava/http/SimpleHttpsServer.java' %}
{% include javafile filename='src/com/rememberjava/http/StaticFileHandler.java' %}



[jsse]: http://docs.oracle.com/javase/8/docs/technotes/guides/security/jsse/JSSERefGuide.html
[jdk8-crypto-algo]: http://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html
[jdk8-crypto-providers]: http://docs.oracle.com/javase/8/docs/technotes/guides/security/SunProviders.html

[jdk8-unlimited]: http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html

[SSLContext]: https://docs.oracle.com/javase/8/docs/api/javax/net/ssl/SSLContext.html
[KeyStore]: https://docs.oracle.com/javase/8/docs/api/java/security/KeyStore.html

[oracle-blog-tls-default]: https://blogs.oracle.com/java-platform-group/jdk-8-will-use-tls-12-as-default
[oracle-blog-tls-diagnosing]: https://blogs.oracle.com/java-platform-group/diagnosing-tls,-ssl,-and-https

[http-example]: /http/2017/01/20/simple_http_server.html

[keytool]: http://docs.oracle.com/javase/7/docs/technotes/tools/solaris/keytool.html

[ecc-intro]: https://www.digicert.com/ecc.htm
[ecc-apache]: https://www.digicert.com/ecc-csr-creation-ssl-installation-apache.htm
[ecc-openssl]: https://wiki.openssl.org/index.php/Command_Line_Elliptic_Curve_Operations
