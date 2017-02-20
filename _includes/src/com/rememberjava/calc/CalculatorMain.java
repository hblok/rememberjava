/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
package com.rememberjava.calc;

public class CalculatorMain {

  public static void main(String[] args) {
    Controller controller = new Controller();
    UiFrame view = new UiFrame(controller);
    controller.setView(view);
  }
}
