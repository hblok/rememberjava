package com.rememberjava.calc;

import java.math.BigDecimal;

public class Model {

  private Tree<Expression> tree;

  private Node<Expression> current;

  Model() {
    clear();
  }

  void clear() {
    tree = new Tree<>(new Value("0"));
    current = tree.getRoot();
  }

  void addDigit(String d) {
    Expression exp = current.getElement();

    if (exp instanceof Value) {
      ((Value) exp).append(d);
    } else if (exp instanceof Operator) {
      Node<Expression> child = new Node<>(new Value(d));
      current.addChild(child);
      current = child;
    }
  }

  void addOperator(Operator o) {
    if (current.getElement() instanceof Operator) {
      throw new IllegalArgumentException();
    }

    Node<Expression> parent = new Node<>(o);
    parent.addChild(current);

    if (current == tree.getRoot()) {
      tree.setRoot(parent);
    }

    current = parent;
  }

  public void calculate() {
    BigDecimal value = evaluate();
    tree.setRoot(new Node<>(new Value("" + value)));
    current = tree.getRoot();
  }

  public BigDecimal evaluate() {
    return getNodeValue(tree.getRoot());
  }

  private BigDecimal getNodeValue(Node<Expression> n) {
    if (n == null) {
      return BigDecimal.ZERO;
    }

    Expression e = n.getElement();

    if (e instanceof Value) {
      return ((Value) e).getValue();
    } else if (e instanceof Operator) {
      return ((Operator) e).apply(getNodeValue(n.getFirstChild()),
          getNodeValue(n.getSecondChild()));
    }

    return BigDecimal.ZERO;
  }

  public String getDisplay() {
    return getNodeDisplay(tree.getRoot());
  }

  private String getNodeDisplay(Node<Expression> n) {
    if (n == null) {
      return "";
    }

    Expression e = n.getElement();

    if (e instanceof Value) {
      return ((Value) e).getValue().toString();
    } else if (e instanceof Expression) {
      return getNodeDisplay(n.getFirstChild()) + " " + ((Operator) e).getDisplay() + " "
          + getNodeDisplay(n.getSecondChild());
    }

    return "";
  }
}
