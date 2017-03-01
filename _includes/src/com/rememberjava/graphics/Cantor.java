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

  public void paint(Graphics g) {
    g.setColor(Color.white);
    g.fillRect(0, 0, getWidth(), getHeight());
    g.setColor(Color.black);

    drawCantor(450, 450, 400, Math.PI / 2., 7, g);
  }

  void drawCantor(int x, int y, int r, double a, int times, Graphics g) {
    System.out.printf("x=%d, y=%d, r=%d, times=%d\n", x, y, r, times);

    if (times > 0) {
      drawMidCircle(x, y, r, g);
      drawCantor(x + r / 2, y, r / 2, 0, times - 1, g);
      drawCantor(x - r / 2, y, r / 2, 0, times - 1, g);
    }
  }

  void drawMidCircle(int x, int y, int r, Graphics g) {
    g.drawOval(x - r, y - r, 2 * r, 2 * r);
  }
}
