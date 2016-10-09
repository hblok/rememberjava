package com.rememberjava.junit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class JunitTestMethodsOrder {

  private static int a;
  private int b;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    a = 1;
    System.out.println(a + " beforeClass");
  }

  @Before
  public void setUp() throws Exception {
    a++;
    b++;
    System.out.println(a + " setup " + b);
  }

  @Test
  public void firstTest() {
    a++;
    b++;
    System.out.println(a + " first " + b);
    assertTrue(a > 2);
  }

  @Test
  public void secondTest() {
    a++;
    b++;
    System.out.println(a + " second " + b);
    assertFalse(false);
  }

  @After
  public void tearDown() throws Exception {
    a++;
    b++;
    System.out.println(a + " tearDown " + b);
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    a++;
    System.out.println(a + " afterClass");
  }
}
