/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
package com.rememberjava.calc;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ModelTest {

  private Model model;

  @Before
  public void setup() {
    model = new Model();
  }

  @Test
  public void testEmpty() {
    assertModel(0, "0");
  }

  @Test
  public void testSingleDigit() {
    model.addDigit("1");
    assertModel(1, "1");
  }

  @Test
  public void testDecimal() {
    model.addDigit(".");
    assertModel(0, "0.0");

    model.addDigit("1");
    assertModel(0.1, "0.1");
  }

  @Test
  public void testPartialOperation() {
    model.addDigit("1");
    model.addOperator(Operator.PLUS);

    assertEquals("1 + ", model.getDisplay());
  }

  @Test
  public void testPlus() {
    model.addDigit("1");
    model.addOperator(Operator.PLUS);
    model.addDigit("1");
    assertModel(2, "1 + 1");
    
    model.calculate();
    assertModel(2, "2");
  }

  @Test
  public void testMinus() {
    model.addDigit("3");
    model.addOperator(Operator.MINUS);
    model.addDigit("1");
    assertModel(2, "3 - 1");
  }

  @Test
  public void testMultiply() {
    model.addDigit("2");
    model.addOperator(Operator.MULTIPLY);
    model.addDigit("2");
    assertModel(4, "2 * 2");
  }

  @Test
  public void testDivide() {
    model.addDigit("3");
    model.addOperator(Operator.DIVIDE);
    model.addDigit("2");
    assertModel(1.5, "3 / 2");
  }

  private void assertModel(double expectedValue, String expectedString) {
    assertEquals(expectedValue, model.evaluate().doubleValue(), 0.001);

    assertEquals(expectedString, model.getDisplay());
  }
}
