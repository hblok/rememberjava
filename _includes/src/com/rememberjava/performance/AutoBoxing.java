/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
package com.rememberjava.performance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AutoBoxing {

  private static final int GIGA = 1000000000;

  @Rule
  public TestName name = new TestName();

  private long start;

  private int iterations;

  public AutoBoxing(Integer iterations) {
    this.iterations = iterations;
  }

  @BeforeClass
  public static void warmup() {
    AutoBoxing autoBoxing = new AutoBoxing(2);
    autoBoxing.incrementPrimitiveInt();
    autoBoxing.incrementBoxedInteger();
    autoBoxing.incrementPrimitiveLong();
    autoBoxing.incrementBoxedLong();
  }

  @Before
  public void setUp() throws Exception {
    start = System.nanoTime();
  }

  @After
  public void tearDown() throws Exception {
    long timeMs = System.nanoTime() - start;
    timeMs /= 1000000;
    String methodName = name.getMethodName().split("\\[")[0];
    System.out.printf("%-25s %2d: %,7d\n", methodName, iterations, timeMs);
  }

  @Test
  public void incrementPrimitiveInt() {
    int a = Integer.MAX_VALUE - 1;
    for (int i = 0; i < iterations; i++) {
      for (int k = 0; k < GIGA; k++) {
        a -= i;
        a *= i;
      }
    }
    String s = "" + a;
    s.toString();
  }

  @Test
  public void incrementBoxedInteger() {
    Integer a = Integer.MAX_VALUE - 1;
    for (int i = 0; i < iterations; i++) {
      for (int k = 0; k < GIGA; k++) {
        a -= i;
        a *= i;
      }
    }
    String s = "" + a;
    s.toString();
  }

  @Test
  public void incrementPrimitiveLong() {
    long a = Integer.MAX_VALUE - 1;
    for (int i = 0; i < iterations; i++) {
      for (int k = 0; k < GIGA; k++) {
        a -= i;
        a *= i;
      }
    }
    String s = "" + a;
    s.toString();
  }

  @Test
  public void incrementBoxedLong() {
    Long a = (long) (Integer.MAX_VALUE - 1);
    for (int i = 0; i < iterations; i++) {
      for (int k = 0; k < GIGA; k++) {
        a -= i;
        a *= i;
      }
    }
    String s = "" + a;
    s.toString();
  }

  @Parameters
  public static Collection<Object> runs() {
    List<Object> result = new ArrayList<>();
    for (int i = 2; i <= 10; i++) {
      result.add(new Object[] { i });
    }
    return result;
  }
}

// http://stackoverflow.com/questions/19844048/why-is-long-slower-than-int-in-x64-java/19847175#19847175
// http://stackoverflow.com/questions/2842695/what-is-microbenchmarking/2842707#2842707
// https://en.wikipedia.org/wiki/Loop_unrolling

// https://docs.oracle.com/javase/tutorial/java/data/autoboxing.html

