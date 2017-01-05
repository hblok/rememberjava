package com.rememberjava.calc;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {

  private T element;

  private Node<T> parent;

  private List<Node<T>> chilrden;

  public Node(T e) {
    element = e;
    chilrden = new ArrayList<>();
  }

  public T getElement() {
    return element;
  }

  public void setElement(T element) {
    this.element = element;
  }

  public Node<T> getParent() {
    return parent;
  }

  public void setParent(Node<T> parent) {
    this.parent = parent;
  }

  public List<Node<T>> getChilrden() {
    return chilrden;
  }

  public Node<T> getFirstChild() {
    return chilrden.size() > 0 ? chilrden.get(0) : null;
  }

  public Node<T> getSecondChild() {
    return chilrden.size() > 1 ? chilrden.get(1) : null;
  }

  public void setChilrden(List<Node<T>> chilrden) {
    this.chilrden = chilrden;
  }

  public void addChild(Node<T> child) {
    chilrden.add(child);
  }
}
