package com.rememberjava.graphics;

import static java.lang.Math.*;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

/**
 * Draws recursive spinning circles with colors.
 */
@SuppressWarnings("serial")
class CantorColors extends JFrame {

  private Canvas canvas;

  private JPanel controls;

  private JPanel colorOut;

  private String colorsList = "#000fff,#18A3AC,#F48024,#178CCB,#052049,#FBAF3F,#000fff";

  private Color[] colors;
  // "#18A3AC,#052049,#178CCB ,#FBAF3F,#F48024,#18A3AC,#052049,#178CCB"

  // #722059,#A92268,#E02477,#DB7580,#D6C689,#722059,#A92268,#E02477
  // #F28E98,#FF9985,#E1CBA6,#DFCFB8,#E7E4D3,#F28E98,#FF9985,#E1CBA6

  private double angle;
  private double angleStepSize;
  private double alternateSign;

  private int sleepMs;

  private float transparency;

  private int recursions;

  private boolean cantor;

  private boolean clearScreen;

  public static void main(String args[]) {
    CantorColors cc = new CantorColors();
    cc.initUi();
    cc.start();
  }

  private void initUi() {
    setLayout(new BorderLayout());

    canvas = new Canvas();
    getContentPane().add(canvas, BorderLayout.CENTER);

    controls = new JPanel();
    controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
    getContentPane().add(controls, BorderLayout.NORTH);

    JSlider stepSize = addSlider("Rotation step size", 1, 100, 5);
    stepSize.addChangeListener(e -> {
      angleStepSize = log(stepSize.getValue());
    });

    JSlider delayMs = addSlider("Frame delay", 0, 100, 0);
    delayMs.addChangeListener(e -> {
      sleepMs = delayMs.getValue();
    });

    JSlider rec = addSlider("Recursions", 1, 20, 7);
    rec.addChangeListener(e -> {
      recursions = rec.getValue();
    });

    JSlider trans = addSlider("Transparency", 1, 1000, 1000);
    trans.addChangeListener(e -> {
      transparency = (float) trans.getValue();
      colors = decodeColors(colorsList);
      showColors();
    });

    JTextField colorIn = new JTextField(colorsList);
    colorIn.addKeyListener(new KeyAdapter() {
      public void keyTyped(KeyEvent e) {
        colorsList = colorIn.getText();
        colors = decodeColors(colorsList);
        showColors();
      }
    });
    controls.add(colorIn);

    colorOut = new JPanel();
    colorOut.setLayout(new BoxLayout(colorOut, BoxLayout.X_AXIS));
    colors = decodeColors(colorsList);
    showColors();
    controls.add(colorOut);

    JPanel buttons = new JPanel();
    buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
    controls.add(buttons);

    JToggleButton cantorSwitch = new JToggleButton("Bisect vs. Cantor");
    cantorSwitch.setSelected(false);
    cantorSwitch.addActionListener(e -> {
      cantor = cantorSwitch.isSelected();
    });
    buttons.add(cantorSwitch);

    JToggleButton alternateSwitch = new JToggleButton("Alternate rotation");
    alternateSwitch.setSelected(true);
    alternateSwitch.addActionListener(e -> {
      alternateSign = alternateSwitch.isSelected() ? -1 : 1;
    });
    buttons.add(alternateSwitch);

    JButton clearButton = new JButton("Clear");
    clearButton.addActionListener(l -> {
      clearScreen = true;
    });
    buttons.add(clearButton);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(600, 600);
    setVisible(true);
  }

  private JSlider addSlider(String text, int min, int max, int value) {
    JPanel row = new JPanel();
    row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
    controls.add(row);

    JLabel label = new JLabel(text);
    label.setPreferredSize(new Dimension(150, (int) label.getPreferredSize().getHeight()));
    row.add(label);

    JSlider slider = new JSlider(min, max, value);
    row.add(slider);

    return slider;
  }

  private void showColors() {
    colorOut.removeAll();
    colorOut.setBackground(Color.WHITE);
    for (Color c : colors) {
      JPanel l = new JPanel();
      l.setPreferredSize(
          new Dimension(getWidth() / colors.length, (int) l.getPreferredSize().getHeight()));
      l.setBackground(c);
      colorOut.add(l);
    }
    colorOut.revalidate();
    colorOut.repaint();
  }

  private void start() {
    transparency = 1000f;
    angleStepSize = 0.005;
    alternateSign = -1;
    recursions = 7;
    colors = decodeColors(colorsList);
    showColors();

    canvas.createBufferStrategy(3);
    BufferStrategy strategy = canvas.getBufferStrategy();

    new Thread(() -> {
      while (true) {
        Graphics g = strategy.getDrawGraphics();
        render(g);
        g.dispose();
        strategy.show();

        sleep(sleepMs);
      }
    }).start();
  }

  /**
   * Given a string of comma separated hex encoded RGB values, creates the
   * individual Color objects. All spaces are removed and ignored; the hex
   * values can be prefixed by a hash (#) or not. Adds the alpha transparency
   * setting from the UI.
   */
  private Color[] decodeColors(String hexColors) {
    String[] split = hexColors.replaceAll(" +", "").split(",");
    Color[] result = new Color[split.length];

    for (int i = 0; i < split.length; i++) {
      String str = (split[i].startsWith("#") ? "" : "#") + split[i];
      float[] comp = Color.decode(str).getRGBComponents(null);
      float a = max(0, min(1f, ((float) i) / transparency));
      result[i] = new Color(comp[0], comp[1], comp[2], a);
    }

    return result;
  }

  private void sleep(long ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {}
  }

  private void render(Graphics g) {
    if (clearScreen) {
      clear(g);
      clearScreen = false;
    }

    int size = min(canvas.getWidth(), canvas.getHeight());
    drawCantor(size / 2, size / 2, (int) (size * 0.4), angle, recursions, g);
    angle += angleStepSize;
  }

  private void drawCantor(int x, int y, int r, double a, int times, Graphics g) {
    if (times <= 0) {
      return;
    }

    g.setColor(colors[times % colors.length]);
    // g.setColor(colors[times - 1]);
    drawMidCircle(x, y, r, g);

    double nextAngel = alternateSign * a;

    if (cantor) {
      // Cantor set
      int x1 = (int) (r / 3 * cos(a));
      int y1 = (int) (r / 3 * sin(a));
      drawCantor(x + 2 * x1, y + 2 * y1, r / 3, nextAngel, times - 1, g);
      drawCantor(x - 2 * x1, y - 2 * y1, r / 3, nextAngel, times - 1, g);
    } else {
      // Bisect
      int x1 = (int) (r / 2 * cos(a));
      int y1 = (int) (r / 2 * sin(a));
      drawCantor(x + x1, y + y1, r / 2, nextAngel, times - 1, g);
      drawCantor(x - x1, y - y1, r / 2, nextAngel, times - 1, g);
    }
  }

  private void drawMidCircle(int x, int y, int r, Graphics g) {
    g.drawOval(x - r, y - r, 2 * r, 2 * r);
  }

  private void clear(Graphics g) {
    g.setColor(Color.white);
    g.fillRect(0, 0, getWidth(), getHeight());
  }
}
