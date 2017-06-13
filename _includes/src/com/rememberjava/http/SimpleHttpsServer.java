/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
package com.rememberjava.http;

import java.io.File;
import java.io.FileInputStream;
import java.lang.ProcessBuilder.Redirect;
import java.net.InetSocketAddress;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;

/**
 * A HTTPS server using a self-signed TLS 1.2 key and certificate generated by
 * the Java keytool command.
 * 
 * Once running, connect to https://localhost:9999/secure/test.txt
 */
@SuppressWarnings("restriction")
public class SimpleHttpsServer {

  private static final File KEYSTORE_FILE = new File(System.getProperty("java.io.tmpdir"),
      "test.jks");

  private static final String KEYSTORE_PASSWORD = "pass_store";

  private static final String KEY_PASSWORD = "pass_key";

  private static final String BASEDIR = "com/rememberjava/http";

  private static final int PORT = 9999;

  public static void main(String[] args) throws Exception {
    System.setProperty("javax.net.debug", "all");

    generateCertificate();

    new SimpleHttpsServer().start();
  }

  /**
   * Generates a new self-signed certificate in /tmp/test.jks, if it does not
   * already exist.
   */
  static void generateCertificate() throws Exception {
    File keytool = new File(System.getProperty("java.home"), "bin/keytool");

    String[] genkeyCmd = new String[] {
        keytool.toString(),
        "-genkey",
        "-keyalg", "RSA",
        "-alias", "some_alias",
        "-validity", "365",
        "-keysize", "2048",
        "-dname", "cn=John_Doe,ou=TestOrgUnit,o=TestOrg,c=US",
        "-keystore", KEYSTORE_FILE.getAbsolutePath(),
        "-storepass", KEYSTORE_PASSWORD,
        "-keypass", KEY_PASSWORD};

    System.out.println(String.join(" ", genkeyCmd));

    ProcessBuilder processBuilder = new ProcessBuilder(genkeyCmd);
    processBuilder.redirectErrorStream(true);
    processBuilder.redirectOutput(Redirect.INHERIT);
    processBuilder.redirectError(Redirect.INHERIT);
    Process exec = processBuilder.start();
    exec.waitFor();

    System.out.println("Exit value: " + exec.exitValue());
  }

  void start() throws Exception {
    HttpsServer httpsServer = HttpsServer.create(new InetSocketAddress(PORT), 0);

    SSLContext sslContext = getSslContext();
    httpsServer.setHttpsConfigurator(new HttpsConfigurator(sslContext));

    httpsServer.createContext("/secure", new StaticFileHandler(BASEDIR));
    httpsServer.start();
  }

  private SSLContext getSslContext() throws Exception {
    KeyStore ks = KeyStore.getInstance("JKS");
    ks.load(new FileInputStream(KEYSTORE_FILE), KEYSTORE_PASSWORD.toCharArray());

    KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
    kmf.init(ks, KEY_PASSWORD.toCharArray());

    TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
    tmf.init(ks);

    SSLContext sslContext = SSLContext.getInstance("TLS");
    sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

    return sslContext;
  }
}