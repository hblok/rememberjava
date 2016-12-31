package com.rememberjava.lambda;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
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

  private static final int RANGE = 15;

  private static final int AXIS_WIDTH = 35;

  private static final int WORK_UNIT_MILLIS = 100;

  private List<StringBuilder> results = new Vector<>(RANGE);

  private TemporalAmount start;

  private IntStream range;

  @Before
  public void setup() {
    String methodName = name.getMethodName().split("\\[")[0];
    System.out.println("\n --- " + methodName + " ---");

    start = Duration.between(Instant.EPOCH, Instant.now());

    range = IntStream.range(1, RANGE);
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
  public void parallelWork() {
    System.out.printf("CPU count: %d\n\n", Runtime.getRuntime().availableProcessors());
    printAxis(true);

    range.parallel().forEach(this::work);

    results.stream().map(sb -> sb.toString()).forEach(System.out::println);
    printAxis(false);
  }

  void work(int units) {
    StringBuilder result = new StringBuilder();
    results.add(result);

    char[] timestampSlots = new char[AXIS_WIDTH];
    Arrays.fill(timestampSlots, ' ');

    long startMillis = getNowMillis();

    try {
      for (int i = 0; i < units; i++) {
        Thread.sleep(WORK_UNIT_MILLIS);

        int nowSlot = (int) (getNowMillis() / WORK_UNIT_MILLIS);
        timestampSlots[nowSlot] = '#';
      }
    } catch (InterruptedException e) { // ignore
    }

    long endMillis = getNowMillis();

    result.append(String.format("%2d: %s [%4d - %4d]", units, new String(timestampSlots),
        startMillis, endMillis));
  }

  private void printAxis(boolean header) {
    String row1 = "";
    String row2 = "";
    for (int i = 0; i < AXIS_WIDTH - 1; i++) {
      row1 += i / 10;
      row2 += i % 10;
    }

    if (header) {
      System.out.println("    " + row1);
      System.out.println("    " + row2);
    } else {
      System.out.println("    " + row2);
      System.out.println("    " + row1);
    }
  }

  private long getNowMillis() {
    return Instant.now().minus(start).toEpochMilli();
  }
}
