/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
package com.rememberjava.graphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

/**
 * Draws recursive circles
 */
@SuppressWarnings("serial")
class Cantor extends JFrame {

  public static void main(String args[]) {
    new Cantor();
  }

  Cantor() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(900, 900);
    setVisible(true);
  }

  @Override
  public void paint(Graphics g) {
    g.setColor(Color.white);
    g.fillRect(0, 0, getWidth(), getHeight());
    g.setColor(Color.black);

    drawCircles(450, 450, 400, 7, g);
  }

  private void drawCircles(int x, int y, int radius, int times, Graphics g) {
    System.out.printf("x=%d, y=%d, r=%d, times=%d\n", x, y, radius, times);

    if (times > 0) {
      drawMidCircle(x, y, radius, g);
      drawCircles(x + radius / 2, y, radius / 2, times - 1, g);
      drawCircles(x - radius / 2, y, radius / 2, times - 1, g);
    }
  }

  private void drawMidCircle(int x, int y, int radius, Graphics g) {
    g.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);
  }
}
