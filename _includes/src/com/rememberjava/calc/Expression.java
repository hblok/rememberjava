/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
package com.rememberjava.calc;

public class Expression {

  protected Number parseValue(String value) {
    try {
      return Integer.parseInt(value);
    } catch (Exception e) {
      try {
        return Double.parseDouble(value);
      } catch (Exception e2) {
      }
    }
    return null;
  }
}
