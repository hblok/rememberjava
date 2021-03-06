/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
package com.rememberjava.http;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public class SimpleHttpServer {

  private static final String BASEDIR = "com/rememberjava/http";

  private static final int PORT = 9999;

  private HttpServer server;

  public static void main(String[] args) throws Exception {
    SimpleHttpServer server = new SimpleHttpServer();
    server.start();
  }

  void start() throws IOException {
    server = HttpServer.create(new InetSocketAddress(PORT), 0);

    server.createContext("/static", new StaticFileHandler(BASEDIR));

    server.start();
  }

  public void stop() {
    server.stop(0);
  }
}
