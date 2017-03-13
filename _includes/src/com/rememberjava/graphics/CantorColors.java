package com.rememberjava.graphics;

import static java.awt.Color.BLUE;
import static java.awt.Color.CYAN;
import static java.awt.Color.GREEN;
import static java.awt.Color.MAGENTA;
import static java.awt.Color.ORANGE;
import static java.awt.Color.RED;
import static java.awt.Color.YELLOW;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Arrays;

import javax.swing.JFrame;

/**
 * Draws recursive spinning circles with colors.
 */
@SuppressWarnings("serial")
class CantorColors extends JFrame {

  // private final Color[] COLORS = new Color[] { BLUE, RED, YELLOW, GREEN,
  // MAGENTA, ORANGE, CYAN, };
  private final Color[] COLORS = decodeColors("fff", 
      "18A3AC", 
      "F48024", 
      "178CCB", 
      "052049", 
      "FBAF3F", 
      "fff");
  //#18A3AC,#052049,#178CCB ,#FBAF3F,#F48024,#18A3AC,#052049,#178CCB
  
  private final int size;

  private double angle;

  public static void main(String args[]) {
    new CantorColors(800);
  }

  CantorColors(int size) {
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

        sleep(20);
      }
    }).start();
  }

  private Color[] decodeColors(String... hexColors) {
    return Arrays.asList(hexColors).stream().map(str -> {
      return Color.decode("#" + str);
    }).toArray(Color[]::new);
  }

  private void sleep(long ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
    }
  }

  private void render(Graphics g) {
    // clear(g);

    drawCantor(size / 2, size / 2, (int) (size * 0.4), angle, 7, g);
    angle += 0.01;
  }

  private void clear(Graphics g) {
    g.setColor(Color.white);
    g.fillRect(0, 0, getWidth(), getHeight());
    g.setColor(Color.black);
  }

  private void drawCantor(int x, int y, int r, double a, int times, Graphics g) {
    if (times <= 0) {
      return;
    }

    g.setColor(COLORS[times - 1]);
    drawMidCircle(x, y, r, g);

    int x1 = (int) (r / 2 * cos(a));
    int y1 = (int) (r / 2 * sin(a));
    drawCantor(x + x1, y + y1, r / 2, -a, times - 1, g);
    drawCantor(x - x1, y - y1, r / 2, -a, times - 1, g);
  }

  private void drawMidCircle(int x, int y, int r, Graphics g) {
    g.drawOval(x - r, y - r, 2 * r, 2 * r);
  }
}
