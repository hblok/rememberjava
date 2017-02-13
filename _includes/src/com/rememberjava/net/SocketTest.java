package com.rememberjava.net;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import org.junit.Test;

/**
 * Simple client / server Socket tests, including a Buffered PrintWriter which
 * has to be flushed.
 */
public class SocketTest {

  private static final int PORT = 8887;

  private OutputStream serverOut;
  private InputStream serverIn;

  private Semaphore lock = new Semaphore(0);

  @Test
  public void testClientServer() throws IOException, InterruptedException {
    ServerSocket server = new ServerSocket(PORT);
    listen(server);

    Socket client = new Socket("localhost", PORT);
    OutputStream clientOut = client.getOutputStream();
    InputStream clientIn = client.getInputStream();

    System.out.println("Waiting for lock");
    lock.acquire();
    System.out.println("Acquired lock");

    write(clientOut, "Hi");
    assertRead(serverIn, "Hi");

    write(serverOut, "Hello");
    assertRead(clientIn, "Hello");

    printWrite(clientOut, "Test printWrite");
    assertRead(serverIn, "Test printWrite");

    client.close();
    server.close();
  }

  private void write(OutputStream out, String str) throws IOException {
    out.write(str.getBytes());
    out.flush();
  }

  private void printWrite(OutputStream out, String str) throws IOException {
    PrintWriter pw = new PrintWriter(out);
    pw.print(str);
    pw.flush();
  }

  private void assertRead(InputStream in, String expected) throws IOException {
    assertEquals("Too few bytes available for reading: ", expected.length(), in.available());

    byte[] buf = new byte[expected.length()];
    in.read(buf);
    assertEquals(expected, new String(buf));
  }

  private void listen(ServerSocket server) {
    new Thread(() -> {
      try {
        Socket socket = server.accept();
        System.out.println("Incoming connection: " + socket);

        serverOut = socket.getOutputStream();
        serverIn = socket.getInputStream();

        lock.release();
        System.out.println("Released lock");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }).start();
  }
}
