/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
package com.rememberjava.graphics;

import static java.lang.Math.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

/**
 * Draws recursive spinning circles.
 * 
 * Uses Double Buffering for smooth rendering. See:
 * http://docs.oracle.com/javase/tutorial/extra/fullscreen/bufferstrategy.html
 */
@SuppressWarnings("serial")
class CantorSpin extends JFrame {

  private final int size;

  private double angle;

  public static void main(String args[]) {
    new CantorSpin(800);
  }

  CantorSpin(int size) {
    this.size = size;

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(size, size);
    setVisible(true);

    createBufferStrategy(3);
    BufferStrategy strategy = getBufferStrategy();

    new Thread(() -> {
      while (true) {
        Graphics g = strategy.getDrawGraphics();
        render(g);
        g.dispose();
        strategy.show();
      }
    }).start();
  }

  private void render(Graphics g) {
    clear(g);

    drawCircles(size / 2, size / 2, (int) (size * 0.4), angle, 7, g);
    angle += 0.01;
  }

  private void clear(Graphics g) {
    g.setColor(Color.white);
    g.fillRect(0, 0, getWidth(), getHeight());
    g.setColor(Color.black);
  }

  private void drawCircles(int x, int y, int r, double a, int times, Graphics g) {
    if (times <= 0) {
      return;
    }

    drawMidCircle(x, y, r, g);

    int x1 = (int) (r / 2 * cos(a));
    int y1 = (int) (r / 2 * sin(a));
    drawCircles(x + x1, y + y1, r / 2, -a, times - 1, g);
    drawCircles(x - x1, y - y1, r / 2, -a, times - 1, g);
  }

  private void drawMidCircle(int x, int y, int r, Graphics g) {
    g.drawOval(x - r, y - r, 2 * r, 2 * r);
  }
}
