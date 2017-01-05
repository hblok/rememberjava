package com.rememberjava.calc;

enum Button {
  ZERO("0", ButtonType.DIGIT),
  ONE("1", ButtonType.DIGIT),
  TWO("2", ButtonType.DIGIT),
  THREE("3", ButtonType.DIGIT),
  FOUR("4", ButtonType.DIGIT),
  FIVE("5", ButtonType.DIGIT),
  SIX("6", ButtonType.DIGIT),
  SEVEN("7", ButtonType.DIGIT),
  EIGHT("8", ButtonType.DIGIT),
  NINE("9", ButtonType.DIGIT),
  POINT(".", ButtonType.DIGIT),
  
  PLUS(Operator.PLUS),
  MINUS(Operator.MINUS),
  MUL(Operator.MULTIPLY),
  DIV(Operator.DIVIDE),
  
  EQUAL("=", ButtonType.EQUAL);

  private String text;
  private ButtonType type;
  private Operator operator;

  private Button(String text, ButtonType type) {
    this(text, type, null);
  }

  private Button(Operator operator) {
    this(operator.getDisplay(), ButtonType.OPERATOR, operator);
  }

  private Button(String text, ButtonType type, Operator operator) {
    this.text = text;
    this.type = type;
    this.operator = operator;
  }

  String getText() {
    return text;
  }

  ButtonType getType() {
    return type;
  }

  Operator getOperator() {
    return operator;
  }
}
