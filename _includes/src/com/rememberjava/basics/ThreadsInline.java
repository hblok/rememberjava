/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
package com.rememberjava.basics;

import java.util.Date;

public class ThreadsInline {

  public static void main(String[] args) {
    new Thread() {
      public void run() {
        System.out.println(new Date().getTime() + " Starting thread A");
        someWork(500);
        System.out.println(new Date().getTime() + " Done with thread A");
      }
    }.start();

    new Thread(() -> {
      System.out.println(new Date().getTime() + " Starting thread B");
      someWork(100);
      System.out.println(new Date().getTime() + " Done with thread B");
    }).start();
  }

  static void someWork(long ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) { // ignore
    }
  }
}
