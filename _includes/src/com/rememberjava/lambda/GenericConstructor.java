/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
package com.rememberjava.lambda;

import static org.junit.Assert.assertEquals;

import java.util.function.BiFunction;

import org.junit.Test;

public class GenericConstructor {

  private static final String TEST = "test";
  private static final int _123 = 123;

  class Generator<T> {

    private final BiFunction<String, Integer, T> constructor;

    Generator(BiFunction<String, Integer, T> constructor) {
      this.constructor = constructor;
    }

    T generate() {
      return constructor.apply(TEST, _123);
    }
  }

  class A {
    final String content;

    A(String str, int i) {
      content = str + i;
    }
  }

  class B {
    final String str;
    final int i;

    B(String str, int i) {
      this.str = str;
      this.i = i;
    }
  }

  @Test
  public void testGenerateA() {
    Generator<A> genA = new Generator<>(A::new);
    A a = genA.generate();
    assertEquals(TEST + _123, a.content);
  }

  @Test
  public void testGenerateB() {
    Generator<B> genB = new Generator<>(B::new);
    B b = genB.generate();
    assertEquals(TEST, b.str);
    assertEquals(_123, b.i);
  }
}
