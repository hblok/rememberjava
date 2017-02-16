package com.rememberjava.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;
import org.simpleframework.http.core.ContainerSocketProcessor;
import org.simpleframework.http.socket.Frame;
import org.simpleframework.http.socket.FrameChannel;
import org.simpleframework.http.socket.FrameListener;
import org.simpleframework.http.socket.Reason;
import org.simpleframework.http.socket.Session;
import org.simpleframework.http.socket.service.DirectRouter;
import org.simpleframework.http.socket.service.Router;
import org.simpleframework.http.socket.service.RouterContainer;
import org.simpleframework.http.socket.service.Service;
import org.simpleframework.transport.connect.SocketConnection;

/**
 * Tests a combined HTTP and Websocket server using the SimpleFramework API.
 * 
 * See http://www.simpleframework.org/
 */
public class SfWebsocketServerTest {

  private static final int PORT = 48889;

  /**
   * Handles incoming HTTP requests.
   */
  class HttpContainer implements Container {

    @Override
    public void handle(Request req, Response resp) {
      System.out.println("HTTP Request: \n" + req);

      resp.setCode(200);
      try {
        resp.getByteChannel().write(ByteBuffer.wrap("Hello".getBytes()));
        resp.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Handles incoming Websocket requests and establishes the frame channel
   * listener.
   */
  class WebsocketService implements Service {
    @Override
    public void connect(Session session) {
      System.out.println("Session: " + session);
      try {
        FrameChannel channel = session.getChannel();
        channel.register(new WebsocketFrameListener());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Handles incoming Frames from an established channel.
   */
  class WebsocketFrameListener implements FrameListener {

    @Override
    public void onFrame(Session session, Frame frame) {
      System.out.println("onFrame " + frame.getType() + ": " + frame);
    }

    @Override
    public void onError(Session session, Exception cause) {
      System.out.println("onError: " + cause);
    }

    @Override
    public void onClose(Session session, Reason reason) {
      System.out.println("onClose: " + reason);
    }
  }

  private SocketConnection serverSocket;

  private boolean clientClosed;

  @Before
  public void setup() throws IOException, InterruptedException {
    HttpContainer httpContainer = new HttpContainer();
    Router websocketRouter = new DirectRouter(new WebsocketService());
    RouterContainer routerContainer = new RouterContainer(httpContainer, websocketRouter, 1);

    ContainerSocketProcessor server = new ContainerSocketProcessor(routerContainer, 1);
    serverSocket = new SocketConnection(server);

    serverSocket.connect(new InetSocketAddress(PORT));
  }

  @After
  public void close() throws IOException {
    serverSocket.close();
  }

  /**
   * Tests the HTTP part only
   */
  @Test
  public void testHttpRequest() throws Exception {
    URL url = new URL("http://localhost:" + PORT);
    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
    in.lines().forEach(System.out::println);
    in.close();
  }

  /**
   * Sends a HTTP Websocket handshake to the server; prints its response;
   * listens for pings and sends pongs; sends a ping; sends a close and closes
   * the client socket after 20 seconds.
   */
  @Test
  public void testWebsocketRequest() throws IOException, InterruptedException {
    Socket socket = new Socket("localhost", PORT);

    OutputStream rawOut = socket.getOutputStream();
    InputStream rawIn = socket.getInputStream();

    sendHandshake(rawOut);
    receiveHandshake(rawIn);

    pingPongHandler(rawIn, rawOut);

    sendPing(rawOut);

    Thread.sleep(20000);

    clientClosed = true;
    sendClose(rawOut);

    socket.close();
  }

  private void sendClose(OutputStream out) throws IOException {
    sendOpcode(out, 0x88);
  }

  private void sendPing(OutputStream out) throws IOException {
    sendOpcode(out, 0x89);
    System.out.println("Sent ping");
  }

  private void sendPong(OutputStream out) throws IOException {
    sendOpcode(out, 0x8a);
    System.out.println("Sent pong");
  }

  private void sendOpcode(OutputStream out, int code) throws IOException {
    out.write(new byte[] { (byte) code, (byte) 0 });
    out.flush();
  }

  /**
   * Client side. Starts a separate thread which reads the InputStream stream
   * and checks for ping frames. If one is detected, sends a pong on the
   * OutputStream.
   */
  private void pingPongHandler(InputStream in, OutputStream out) {
    final int pingOpCode = 0x89;

    new Thread(() -> {
      while (!clientClosed) {
        try {
          byte[] buf = readInput(in);

          if (buf.length >= 2 && (buf[0] & 0xff) == pingOpCode) {
            sendPong(out);
          } else if (buf.length > 0) {
            System.out.println("From server:");
            for (int i = 0; i < buf.length; i++) {
              System.out.print(Integer.toHexString(buf[i]) + " ");
            }
            System.out.println();
          }

          Thread.sleep(50);
        } catch (IOException | InterruptedException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }

  /**
   * Client side. Reads available bytes from the InputStream.
   */
  private byte[] readInput(InputStream in) throws IOException {
    byte[] buf = new byte[2000];
    int i = 0;
    while (in.available() > 0) {
      buf[i++] = (byte) in.read();
    }

    return Arrays.copyOf(buf, i);
  }

  /**
   * Client side. Sends the Websocket handshake request to the server.
   */
  private void sendHandshake(OutputStream rawOut) {
    String handshake[] = {
      "GET /test HTTP/1.1",
      "Host: loclahost",
      "Upgrade: websocket",
      "Connection: Upgrade",
      "Sec-WebSocket-Key: dGhlIHNhbXBsZSBub25jZQ==",
      "Origin: http://localhost:" + PORT,
      "Sec-WebSocket-Protocol: test",
      "Sec-WebSocket-Version: 13",
      ""
    };

    PrintWriter pwOut = new PrintWriter(rawOut, true);
    for (String str : handshake) {
      pwOut.print(str + "\r\n");
    }
    pwOut.flush();
  }

  /**
   * Client side. Reads the Websocket handshake response from the server.
   */
  private void receiveHandshake(InputStream rawIn) throws IOException {
    BufferedReader bufIn = new BufferedReader(new InputStreamReader(rawIn));

    System.out.println("Response: ");
    String line;
    do {
      line = bufIn.readLine();
      System.out.println("R: '" + line + "'");
    } while (line != null && !line.isEmpty());
  }
}
