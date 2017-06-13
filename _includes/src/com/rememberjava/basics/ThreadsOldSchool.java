/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
package com.rememberjava.basics;

import java.util.Date;

public class ThreadsOldSchool {

  static class MyThread extends Thread {

    private long work;
    private String name;

    MyThread(String name, long work) {
      this.name = name;
      this.work = work;
    }

    @Override
    public void run() {
      System.out.println(new Date().getTime() + " Starting thread " + name);
      someWork(work);
      System.out.println(new Date().getTime() + " Done with thread " + name);
    }

    void someWork(long ms) {
      try {
        Thread.sleep(ms);
      } catch (InterruptedException e) { // ignore
      }
    }
  }

  public static void main(String[] args) {
    MyThread a = new MyThread("A", 500);
    MyThread b = new MyThread("B", 100);
    a.start();
    b.start();
  }
}
