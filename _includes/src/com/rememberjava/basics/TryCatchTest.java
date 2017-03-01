/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
package com.rememberjava.basics;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.junit.Test;

public class TryCatchTest {

  // The second try-block will throw an uncaught ArithmeticException because of
  // the divide by 0.
  @Test(expected = ArithmeticException.class)
  public void basic() {
    try {
      String str = null;
      str.toString();
    } catch (NullPointerException e) {
      e.printStackTrace();
    }

    try {
      int a = 1 / 0;
    } catch (NullPointerException e) {
      System.out.println("This will not trigger.");
    }
    System.out.println("Will not reach this point.");
  }

  @Test
  public void multi() {
    try {
      Integer a = null;
      int b = 1 / a;
    } catch (NullPointerException e) {
      System.out.println("NullPointerException");
    } catch (ArithmeticException e) {
      System.out.println("ArithmeticException");
    }

    try {
      Integer a = null;
      int b = 1 / a;
    } catch (NullPointerException | ArithmeticException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testFinally() {
    OutputStream out = null;
    try {
      out = new FileOutputStream("/dev/null");
      out.write(0);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (out != null) {
        try {
          out.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  @Test
  public void tryWith() {
    try (OutputStream out = new FileOutputStream("/dev/null")) {
      out.write(0);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void tryWithMulti() {
    try (Socket s = new Socket("google.com", 80);
         OutputStream out = s.getOutputStream();
         InputStream in = s.getInputStream()) {
      out.write(0);
      in.read();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void message() {
    File file = new File("/dev/null");
    try (OutputStream out = new FileOutputStream(file)) {
      out.write(0);
    } catch (IOException e) {
      throw new RuntimeException("Could not open or read file " + file.getAbsolutePath(), e);
    }
  }
}
