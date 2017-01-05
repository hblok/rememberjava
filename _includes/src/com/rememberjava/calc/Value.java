package com.rememberjava.calc;

import java.math.BigDecimal;

public class Value extends Expression {

  private BigDecimal value;

  Value(String v) {
    value = new BigDecimal(v);
  }

  void append(String v) {
    value = new BigDecimal(value.toPlainString() + v);
  }

  BigDecimal getValue() {
    return value;
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
