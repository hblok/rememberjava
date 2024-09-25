/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
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

  private int axisWidth;

  private static final int WORK_UNIT_MILLIS = 100;

  private List<StringBuilder> results = new Vector<>(RANGE);

  private TemporalAmount start;

  private IntStream range;

  @Before
  public void setup() {
    String methodName = name.getMethodName().split("\\[")[0];
    System.out.println("\n --- " + methodName + " ---");

    int cpus = Runtime.getRuntime().availableProcessors();
    int workUnits = RANGE * (RANGE + 1) / 2;
    axisWidth = (int) ((workUnits / cpus) * 1.3);

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

  // TODO: Fix index failure
  //@Test
  public void disabled_parallelWork() {
    System.out.printf("CPU count: %d\n\n",
        Runtime.getRuntime().availableProcessors());
    printAxis(true);

    range.parallel().forEach(this::work);

    results.stream().map(sb -> sb.toString()).forEach(System.out::println);
    printAxis(false);
  }

  void work(int units) {
    StringBuilder result = new StringBuilder();
    results.add(result);

    char[] timestampSlots = new char[axisWidth];
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

    result.append(String.format("%2d: %s [%4d - %4d]",
        units, new String(timestampSlots), startMillis, endMillis));
  }

  private void printAxis(boolean header) {
    String row1 = "";
    String row2 = "";
    for (int i = 0; i < axisWidth - 1; i++) {
      row1 += i / 10;
      row2 += i % 10;
    }

    System.out.println("    " + (header ? row1 : row2));
    System.out.println("    " + (header ? row2 : row1));
  }

  private long getNowMillis() {
    return Instant.now().minus(start).toEpochMilli();
  }
}
