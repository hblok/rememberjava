/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
package com.rememberjava.junit;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ExceptionsTest {

  private static final String INVALID_INTEGER = "invalid integer";

  @Rule
  public final ExpectedException thrown = ExpectedException.none();

  @Test
  public void testOldStyle() {
    try {
      Integer.parseInt(INVALID_INTEGER);
      fail("Expected an Exception to be thrown");
    } catch (NumberFormatException e) {
      assertEquals("For input string: \"" + INVALID_INTEGER + "\"", e.getMessage());
    }
  }

  @Test(expected = NumberFormatException.class)
  public void annotation() {
    Integer.parseInt(INVALID_INTEGER);
  }

  @Test
  public void testThrown() {
    thrown.expect(NumberFormatException.class);
    thrown.expectMessage(INVALID_INTEGER);

    Integer.parseInt(INVALID_INTEGER);
  }

  @Test
  public void hamcrest() {
    thrown.expect(NumberFormatException.class);
    thrown.expectMessage(endsWith(INVALID_INTEGER + "\""));

    Integer.parseInt(INVALID_INTEGER);
  }

  @Test
  public void cause() {
    thrown.expect(RuntimeException.class);
    thrown.expectMessage(INVALID_INTEGER);
    thrown.expectCause(isA(NumberFormatException.class));

    try {
      Integer.parseInt(INVALID_INTEGER);
    } catch (NumberFormatException e) {
      throw new RuntimeException(e);
    }
  }
}
