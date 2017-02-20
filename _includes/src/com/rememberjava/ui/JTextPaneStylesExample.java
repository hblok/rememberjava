/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
package com.rememberjava.ui;

import java.awt.event.ActionEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.StyledEditorKit.ItalicAction;

/**
 * Minimal example with JTextPane and StyledDocument. When the string "foo" is
 * typed or pasted it is made bold. When the string "bar" is selected it is made
 * italic.
 */
@SuppressWarnings("serial")
public class JTextPaneStylesExample extends JFrame implements DocumentListener, CaretListener {

  /**
   * Predefined styles.
   */
  enum Style {
    BOLD(StyleConstants.Bold),
    ITALIC(StyleConstants.Italic);

    private MutableAttributeSet attrib;

    private Style(Object style) {
      attrib = new SimpleAttributeSet();
      attrib.addAttribute(style, true);
    }

    AttributeSet get() {
      return attrib;
    }
  }

  private static final String FOO = "foo";

  private static final String BAR = "bar";

  private final StyledDocument doc;

  public static void main(String[] args) {
    new JTextPaneStylesExample();
  }

  public JTextPaneStylesExample() {
    JTextPane editor = new JTextPane();
    doc = editor.getStyledDocument();
    getContentPane().add(editor);

    setSize(500, 500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

    editor.getDocument().addDocumentListener(this);
    editor.addCaretListener(this);
  }

  /**
   * Check of a selection of the string "bar".
   */
  @Override
  public void caretUpdate(CaretEvent event) {
    int dot = event.getDot();
    int mark = event.getMark();
    int start;
    int end;

    if (dot == mark) {
      return;
    } else if (dot < mark) {
      start = dot;
      end = mark;
    } else {
      start = mark;
      end = dot;
    }

    System.out.println(start + ", " + end);
    try {
      if (doc.getText(start, BAR.length()).startsWith(BAR)) {
        ItalicAction action = new StyledEditorKit.ItalicAction();
        action.actionPerformed(new ActionEvent(event.getSource(), 0, ""));

        // Alternative custom update:
        // updateAttribute(start, BAR.length(), Style.ITALIC);
      }
    } catch (BadLocationException e) {
      e.printStackTrace();
    }
  }

  /**
   * Check for the occurrence of the string "foo".
   */
  @Override
  public void insertUpdate(DocumentEvent event) {
    try {
      String text = doc.getText(0, doc.getLength());

      Pattern p = Pattern.compile(FOO);
      Matcher matcher = p.matcher(text);
      while (matcher.find()) {
        updateAttribute(matcher.start(), FOO.length(), Style.BOLD);
      }
    } catch (BadLocationException e) {
      e.printStackTrace();
    }
  }

  /**
   * Update the string at the given position and length with the given style.
   * The update happens on a separate AWT thread, to avoid mutations of the
   * Document model while the event is processing.
   */
  private void updateAttribute(int pos, int len, Style style) {
    SwingUtilities.invokeLater(() -> {
      doc.setCharacterAttributes(pos, len, style.get(), true);
    });
  }

  @Override
  public void removeUpdate(DocumentEvent e) {}

  @Override
  public void changedUpdate(DocumentEvent e) {}
}
