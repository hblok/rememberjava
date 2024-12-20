/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
package com.rememberjava.calc;

public class Tree<T> {

  private Node<T> root;

  public Tree(T element) {
    root = new Node<>(element);
  }

  public Node<T> getRoot() {
    return root;
  }

  public void setRoot(Node<T> node) {
    root = node;
  }
}
