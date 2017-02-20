/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
package com.rememberjava.calc;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

class Controller {

  private UiFrame view;

  private Model model = new Model();

  private ScriptEngine engine;

  Controller() {
    NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
    engine = factory.getScriptEngine();
  }

  void setView(UiFrame view) {
    this.view = view;
  }

  void typed(String text) {
    //model = text;
  }

  void click(Button button) {
    String txt = button.getText();
    ButtonType type = button.getType();

    switch (type) {
    case DIGIT:
      model.addDigit(txt);
      break;
    case OPERATOR:
      model.addOperator(button.getOperator());
      break;
    case EQUAL:
      model.calculate();
      break;
    }

    view.setDisplay(model.getDisplay());
  }
}
