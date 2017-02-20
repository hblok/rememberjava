/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
package com.rememberjava.http;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Half-hearted incomplete implementation of the WebSocket Protocol (RFC 6455).
 * The only thing worthwhile in this code is the forming of response key based
 * on the client key.
 * 
 * This handler will only send a valid handshake response, but fail to maintain
 * the connection. As of writing, it is not possible to use the
 * com.sun.net.httpserver API for WebSockets.
 * 
 * https://tools.ietf.org/html/rfc6455
 *
 */
@SuppressWarnings("restriction")
public class CrippledWebSocketHandler implements HttpHandler {

  private static final String WEB_SOCKET_GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";

  @Override
  public void handle(HttpExchange ex) throws IOException {
    Headers reqHeaders = ex.getRequestHeaders();
    reqHeaders.entrySet().forEach(System.out::println);

    String serverResponseKey = getServerResponseKey(reqHeaders.get("Sec-websocket-key").get(0));

    Headers resHeaders = ex.getResponseHeaders();
    resHeaders.add("Sec-WebSocket-Accept", serverResponseKey);
    resHeaders.add("Upgrade", "websocket");
    resHeaders.add("Connection", "Upgrade");

    ex.sendResponseHeaders(101, -1);

    // When time is up, the connection will be closed, and the browser object
    // will get a call to onClose() on the websocket object.
    sleep(5000);
  }

  private String getServerResponseKey(String clientKey) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-1");
      digest.update(clientKey.getBytes());
      digest.update(WEB_SOCKET_GUID.getBytes());
      byte[] sha1 = digest.digest();

      return Base64.getEncoder().encodeToString(sha1);

    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    return null;
  }

  private void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
