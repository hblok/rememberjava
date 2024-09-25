package com.rememberjava.basics;

import static org.junit.Assert.*;

import org.junit.Test;

// https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
// http://www.darksleep.com/player/JavaAndUnsignedTypes.html
public class PrimitiveTypes {

  @SuppressWarnings("unused")
  @Test
  public void initialization(){
    byte b = 0;
    short s = 0;
    int i = 0;
    long l = 0;
    float f = 0;
    double d = 0;
    char c = 'A';
    boolean bool = true; 
    String str = "";
  }
  
  @Test
  public void numeric() {
    byte b = 127;
    short s = b;
    char c = (char) s;
    int i = c;
    long l = i;
    float f = l;
    double d = f;

    assertEquals(127, d, 0);
  }
  
  @Test 
  public void longToFloat() {
    float f = Long.MAX_VALUE;
    System.out.println("f="+f);
    assertEquals(Long.MAX_VALUE, f, 0);
  }
  
  @Test
  public void test() {
    char c = Character.MAX_VALUE;
    int i = c;
    System.out.println(">> i="+i);
    
    byte b = Byte.MAX_VALUE;
    b++;
    assertTrue(b < 0);
  }

  @Test
  public void testShort() {
    short s = Short.MAX_VALUE;

    char c = Character.MAX_VALUE;
    c++;
    assertTrue(c == 0);
  }

  @Test
  public void testLong() {
      //assertTrue(Long.MAX_VALUE + 1 < 0);
    assertTrue(Long.compareUnsigned(0, Long.MAX_VALUE + 1) < 0);
    
    Math.toIntExact(1L);
    //Math.addExact(Integer.MAX_VALUE, 1);
  }
  
  @Test
  public void testCast() {
    long l = Integer.MAX_VALUE;
    l += 2;
    int i = (int) l;
    System.out.println(i);
  }
}
