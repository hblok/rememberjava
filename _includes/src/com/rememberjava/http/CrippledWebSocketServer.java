package com.rememberjava.http;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

/**
 * This will not work; see details in WebSocketHandler.
 */
@SuppressWarnings("restriction")
public class CrippledWebSocketServer {

  private static final String BASEDIR = "com/rememberjava/http";

  private static final int PORT = 9999;

  public static void main(String[] args) throws IOException {
    HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

    server.createContext("/static", new StaticFileHandler(BASEDIR));
    HttpContext context = server.createContext("/websocket", new CrippledWebSocketHandler());
    context.getAttributes().entrySet().forEach(System.out::println);

    server.start();

    System.out.println("Started on http://localhost:" + PORT + "/static/wstest.html");
  }
}
