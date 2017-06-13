/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
package com.rememberjava.basics;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ThreadFunction {

  public static void main(String[] args) {
    Map<String, Integer> work = new HashMap<>();
    work.put("A", 500);
    work.put("B", 100);

    work.entrySet().stream()
      .map(e -> new Thread(doWork(e.getKey(), e.getValue())))
      .forEach(t -> t.start());

    new Thread(doWork("C", 1000)).start();
  }

  static Runnable doWork(String name, long work) {
    return () -> {
      System.out.println(new Date().getTime() + " Starting thread " + name);
      someWork(work);
      System.out.println(new Date().getTime() + " Done with thread " + name);
    };
  }

  static void someWork(long ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) { // ignore
    }
  }
}
