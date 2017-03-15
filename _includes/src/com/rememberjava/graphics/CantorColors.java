package com.rememberjava.graphics;

import static java.lang.Math.*;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import javax.imageio.ImageIO;
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

  private JPanel colorOut;

  private String colorsList =
      "#000fff,#18A3AC,#F48024,#178CCB,#052049,#FBAF3F,#000fff";
  // "#18A3AC,#052049,#178CCB ,#FBAF3F,#F48024,#18A3AC,#052049,#178CCB";
  // "#722059,#A92268,#E02477,#DB7580,#D6C689,#722059,#A92268,#E02477";
  // "#F28E98,#FF9985,#E1CBA6,#DFCFB8,#E7E4D3,#F28E98,#FF9985,#E1CBA6";

  private Color[] colors;

  private double angle;
  private double angleStepSize;
  private double alternateSign;

  private int sleepMs;

  private float transparency;

  private int recursions;

  private boolean cantor;

  private boolean clearScreen;

  private boolean save;

  private int index;

  private BufferedImage bufImg;

  private Graphics imgG;

  public static void main(String args[]) {
    CantorColors cc = new CantorColors();
    cc.initUi();
    cc.start();
  }

  private void initUi() {
    setLayout(new BorderLayout());

    canvas = new Canvas();
    getContentPane().add(canvas, BorderLayout.CENTER);

    JPanel controls = new JPanel();
    controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
    getContentPane().add(controls, BorderLayout.NORTH);

    addSlider(controls, "Rotation step size", 1, 100, 5, slider -> {
      angleStepSize = log(slider.getValue());
    });

    addSlider(controls, "Frame delay", 0, 100, 0, slider -> {
      sleepMs = slider.getValue();
    });

    addSlider(controls, "Recursions", 1, 20, 7, slider -> {
      recursions = slider.getValue();
    });

    addSlider(controls, "Transparency", 1, 1000, 1000, slider -> {
      transparency = (float) slider.getValue();
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

    addToggleButton(buttons, "Bisect vs. Cantor", false, button -> {
      cantor = button.isSelected();
    });

    addToggleButton(buttons, "Alternate rotation", true, button -> {
      alternateSign = button.isSelected() ? -1 : 1;
    });

    addToggleButton(buttons, "Save PNGs", false, button -> {
      save = button.isSelected();
      index = 0;
    });

    JButton clearButton = new JButton("Clear");
    clearButton.addActionListener(l -> {
      clearScreen = true;
    });
    buttons.add(clearButton);

    canvas.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        createRenderImage();
      }
    });

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(600, 738);
    setVisible(true);
  }

  private void addSlider(JPanel controls, String text, int min, int max, int value,
      Consumer<JSlider> changeListener) {
    JPanel row = new JPanel();
    row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
    controls.add(row);

    JLabel label = new JLabel(text);
    label.setPreferredSize(new Dimension(150, (int) label.getPreferredSize().getHeight()));
    row.add(label);

    JSlider slider = new JSlider(min, max, value);
    row.add(slider);

    slider.addChangeListener(e -> {
      changeListener.accept(slider);
    });
  }

  private void addToggleButton(JPanel buttons, String text, boolean selected,
      Consumer<JToggleButton> actionListener) {
    JToggleButton button = new JToggleButton(text);
    button.setSelected(selected);
    buttons.add(button);

    button.addActionListener(e -> {
      actionListener.accept(button);
    });
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

    createRenderImage();
    Graphics g = canvas.getGraphics();

    new Thread(() -> {
      while (true) {
        render(imgG);

        if (save && index++ % 3 == 0) {
          saveImage(bufImg, index);
        }

        g.drawImage(bufImg, 0, 0, this);

        sleep(sleepMs);
      }
    }).start();
  }

  private void createRenderImage() {
    bufImg = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_ARGB);
    imgG = bufImg.createGraphics();
    clear(imgG);
  }

  private void saveImage(BufferedImage bi, int i) {
    try {
      String name = String.format("/tmp/cantor%05d.png", i);
      ImageIO.write(bi, "PNG", new File(name));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Given a string of comma separated hex encoded RGB values, creates the
   * individual Color objects. All spaces are removed and ignored; the hex
   * values can be prefixed by a hash (#) or not. Adds the alpha transparency
   * setting from the UI.
   */
  private Color[] decodeColors(String hexColors) {
    try {
      String[] split = hexColors.replaceAll(" +", "").split(",");
      Color[] result = new Color[split.length];

      for (int i = 0; i < split.length; i++) {
        String str = (split[i].startsWith("#") ? "" : "#") + split[i];
        float[] comp = Color.decode(str).getRGBComponents(null);
        float a = max(0, min(1f, ((float) i) / transparency));
        result[i] = new Color(comp[0], comp[1], comp[2], a);
      }

      return result;
    } catch (Exception e) {
      System.out.println(e);
      return colors;
    }
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
    drawCantor(size / 2, size / 2, (int) (size * 0.45), angle, recursions, g);
    angle += angleStepSize;
  }

  private void drawCantor(int x, int y, int r, double a, int times, Graphics g) {
    if (times <= 0) {
      return;
    }

    g.setColor(colors[times % colors.length]);
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
    g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
  }
}
