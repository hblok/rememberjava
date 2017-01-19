package com.rememberjava.http;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SimpleHttpServerTest {

  private SimpleHttpServer server;

  @Before
  public void setup() {
    server = new SimpleHttpServer();
  }

  @After
  public void teardown() {
    server.stop();
  }

  @Test
  public void testStart() throws IOException {
    server.start();
  }

  @Test
  public void testDownload() throws IOException {
    server.start();

    URL url = new URL("http://localhost:9999/static/test.txt");
    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
    in.lines().forEach(System.out::println);
    in.close();
  }

  @Test(expected = FileNotFoundException.class)
  public void testFilenNotFound() throws IOException {
    server.start();

    URL url = new URL("http://localhost:9999/static/not_found");
    url.openStream();
  }
}
