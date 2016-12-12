package com.rememberjava.calc;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

class Controller {

  private UiFrame view;

  private String model = "";

  private ScriptEngine engine;

  Controller() {
    NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
    engine = factory.getScriptEngine();
  }

  void setView(UiFrame view) {
    this.view = view;
  }

  void typed(String text) {
    model = text;
  }

  void click(Button button) {
    String txt = button.getText();
    Type type = button.getType();

    switch (type) {
    case DIGIT:
      model += txt;
      break;
    case OPERATOR:
      model += " " + txt + " ";
      break;
    case EQUAL:
      calculate();
      break;
    }

    view.setDisplay(model);
  }

  void calculate() {
    try {
      Object eval = engine.eval(model);
      model = eval.toString();
    } catch (ScriptException e) {
      // If the expression was invalid,
      // don't modify the calculator display.
    }
  }
}
