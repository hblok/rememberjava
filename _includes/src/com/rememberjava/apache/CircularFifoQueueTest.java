package com.rememberjava.apache;

import static org.junit.Assert.assertTrue;

import java.util.stream.Stream;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.junit.Test;

public class CircularFifoQueueTest {

  @Test
  public void testDrop() {
    CircularFifoQueue<Integer> queue = new CircularFifoQueue<>(2);

    queue.add(1);
    queue.add(2);
    queue.add(3);

    assertTrue(2 == queue.poll());
    assertTrue(3 == queue.poll());
    assertTrue(queue.isEmpty());
  }

  @Test
  public void testMultithreaded() {
    CircularFifoQueue<Integer> queue = new CircularFifoQueue<>(5);

    Thread insertThread = new Thread(() -> {
      int i = 0;
      while (true) {
        synchronized (queue) {
          queue.offer(i++);
        }
      }
    });
    insertThread.start();

    sleep(1);
    for (int i = 0; i < 10; i++) {
      synchronized (queue) {
        System.out.println("" + i + ": " + queue.poll());
      }
    }
  }

  @Test
  public void streamFail() {
    CircularFifoQueue<Integer> queue = new CircularFifoQueue<>(10);

    Thread insertThread = new Thread(() -> {
      int i = 0;
      while (true) {
        queue.offer(i++);
      }
    });
    insertThread.start();

    sleep(1000);
    System.out.println("size=" + queue.size());
    // throws NoSuchElementException
    queue.stream().forEach(System.out::println);
  }

  @Test
  public void streamGenerate() {
    CircularFifoQueue<Integer> queue = new CircularFifoQueue<>(10);

    Thread insertThread = new Thread(() -> {
      int i = 0;
      while (true) {
        synchronized (queue) {
          queue.offer(i++);
        }
      }
    });
    insertThread.start();

    Stream.generate(() -> {
      synchronized (queue) {
        return queue.poll();
      }
    })
    // Never stops without the limit
    .limit(20)
    .forEach(System.out::println);
  }

  private void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
