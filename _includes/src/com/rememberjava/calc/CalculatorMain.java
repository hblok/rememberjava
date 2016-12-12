package com.rememberjava.calc;

public class CalculatorMain {

  public static void main(String[] args) {
    Controller controller = new Controller();
    UiFrame view = new UiFrame(controller);
    controller.setView(view);
  }
}
