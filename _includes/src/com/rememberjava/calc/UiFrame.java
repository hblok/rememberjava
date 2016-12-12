package com.rememberjava.calc;

import static com.rememberjava.calc.Button.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

class UiFrame extends JFrame {

  private static final long serialVersionUID = 1L;

  private JPanel topPanel;
  private JPanel buttonPanel;
  private JTextField display;

  private Controller controller;

  UiFrame(Controller controller) {
    super("RJ Calculator");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.controller = controller;

    initUi();
  }

  private void initUi() {
    topPanel = new JPanel();
    topPanel.setLayout(new BorderLayout());

    initDisplay();
    initButtons();

    getContentPane().add(topPanel);

    setSize(350, 200);
    setVisible(true);
  }
  
  private void initDisplay() {
    display = new JTextField();
    topPanel.add(display, BorderLayout.NORTH);

    display.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
    display.setHorizontalAlignment(SwingConstants.RIGHT);
    display.setPreferredSize(new Dimension(350, 40));

    display.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) {
        controller.typed(display.getText());
      }
    });
  }

  private void initButtons() {
    Button[] buttons = {
        SEVEN, EIGHT, NINE, DIV,
        FOUR, FIVE, SIX, MUL,
        ONE, TWO, THREE, MINUS,
        ZERO, POINT, EQUAL, PLUS};
    
    buttonPanel = new JPanel();
    topPanel.add(buttonPanel, BorderLayout.CENTER);

    buttonPanel.setLayout(new GridLayout(4, 4));

    for (Button button : buttons) {
      JButton b = new JButton(button.getText());
      b.addActionListener(l -> {
        controller.click(button);
      });
      b.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
      buttonPanel.add(b);
    }
  }

  void setDisplay(String txt) {
    display.setText(txt);
  }
}
