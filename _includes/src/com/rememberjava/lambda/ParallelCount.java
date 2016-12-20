package com.rememberjava.lambda;

import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class ParallelCount {

  @Rule
  public TestName name = new TestName();

  private static final IntConsumer PRINTLN = System.out::println;

  private IntStream range;

  @Before
  public void setup() {
    String methodName = name.getMethodName().split("\\[")[0];
    System.out.println(methodName);

    range = IntStream.range(1, 10);
  }

  @Test
  public void sequential() {
    range.forEach(PRINTLN);
  }

  @Test
  public void parallel() {
    range.parallel().forEach(PRINTLN);
  }

  @Test
  public void parallelSleep() {
    range.parallel().forEach(this::work);
  }

  void work(int v) {
    try {
      for (int i = 0; i < v; i++) {
        Thread.sleep(100);
        System.out.println(String.format("%d: %" + (i + 1) + "d", v, i).replace(" ", "#"));
      }
    } catch (InterruptedException e) { // ignore
    }
    System.out.printf("%d: done\n", v);
  }
}
