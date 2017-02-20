/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
package com.rememberjava.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;
import org.simpleframework.http.core.ContainerSocketProcessor;
import org.simpleframework.transport.connect.SocketConnection;

public class SfHttpServerTest {

  private static final int PORT = 8889;

  static class StaticFileContainer implements Container {

    private String baseDir;

    StaticFileContainer(String baseDir) {
      this.baseDir = baseDir;
    }

    @Override
    public void handle(Request req, Response resp) {
      System.out.println(req);
      System.out.println("target: " + req.getTarget());

      Path path = Paths.get(baseDir, req.getTarget());

      resp.setCode(200);
      try {
        resp.getByteChannel().write(ByteBuffer.wrap(Files.readAllBytes(path)));
        resp.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private SocketConnection connection;

  @Before
  public void setup() throws IOException {
    StaticFileContainer container = new StaticFileContainer("com/rememberjava/http");
    ContainerSocketProcessor server = new ContainerSocketProcessor(container, 1);
    connection = new SocketConnection(server);

    connection.connect(new InetSocketAddress(PORT));
  }

  @After
  public void close() throws IOException {
    connection.close();
  }

  @Test
  public void testRequest() throws Exception {
    URL url = new URL("http://localhost:8889/test.txt");
    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
    in.lines().forEach(System.out::println);
    in.close();
  }
}
