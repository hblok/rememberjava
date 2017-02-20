/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
package com.rememberjava.calc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.BiFunction;

public class Operator extends Expression {

  public static final Operator PLUS =
      new Operator("+", (a, b) -> { return a.add(b); });
  
  public static final Operator MINUS =
      new Operator("-", (a, b) -> { return a.subtract(b); });

  public static final Operator MULTIPLY =
      new Operator("*", (a, b) -> { return a.multiply(b); });

  public static final Operator DIVIDE =
      new Operator("/", (a, b) -> {
        return a.divide(b, Math.max(a.scale(), b.scale()) + 1, RoundingMode.HALF_UP);
      });

  private final BiFunction<BigDecimal, BigDecimal, BigDecimal> function;

  private final String display;

  public Operator(String str, BiFunction<BigDecimal, BigDecimal, BigDecimal> f) {
    display = str;
    function = f;
  }

  BigDecimal apply(BigDecimal a, BigDecimal b) {
    return function.apply(a, b);
  }

  String getDisplay() {
    return display;
  }

  @Override
  public String toString() {
    return display;
  }
}
