package com.rememberjava.calc;

enum Button {
  ZERO("0", Type.DIGIT),
  ONE("1", Type.DIGIT),
  TWO("2", Type.DIGIT),
  THREE("3", Type.DIGIT),
  FOUR("4", Type.DIGIT),
  FIVE("5", Type.DIGIT),
  SIX("6", Type.DIGIT),
  SEVEN("7", Type.DIGIT),
  EIGHT("8", Type.DIGIT),
  NINE("9", Type.DIGIT),
  POINT(".", Type.DIGIT),
  
  PLUS("+", Type.OPERATOR),
  MINUS("-", Type.OPERATOR),
  MUL("*", Type.OPERATOR),
  DIV("/", Type.OPERATOR),
  
  EQUAL("=", Type.EQUAL);

  private String text;
  private Type type;

  private Button(String text, Type type) {
    this.text = text;
    this.type = type;
  }

  String getText() {
    return text;
  }

  Type getType() {
    return type;
  }
}
