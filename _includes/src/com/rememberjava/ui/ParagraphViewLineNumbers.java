package com.rememberjava.ui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Collectors;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Element;
import javax.swing.text.ParagraphView;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

/**
 * Example use of a left margin line number for a JEditorPane. Using a custom
 * EditorKit and ViewFactory to pass in ParagraphView which paints a child
 * element. Pads the line numbers up to 999, and handles wrapped lines in the
 * editor.
 */
public class ParagraphViewLineNumbers extends JFrame {

  private static final long serialVersionUID = 1L;

  public static void main(String[] args) throws IOException {
    new ParagraphViewLineNumbers();
  }

  public ParagraphViewLineNumbers() throws IOException {
    JEditorPane editor = new JEditorPane();
    editor.setEditorKit(new CustomEditorKit());
    editor.setText(getRandomText());

    JScrollPane scroll = new JScrollPane(editor);
    getContentPane().add(scroll);

    setSize(500, 500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  /**
   * Returns the first 100 words of the default dictionary (on Ubuntu / Debian)
   * and injects random line breaks.
   */
  private String getRandomText() throws IOException {
    Random rnd = new Random();
    return Files.readAllLines(Paths.get("/usr/share/dict/words")).stream().limit(100)
        .map(word -> (rnd.nextInt(15) == 0 ? "\n" : "") + word).collect(Collectors.joining(" "));
  }

  /**
   * Passes in the custom ViewFactory. Inherits from the StyledEditorKit since
   * that already comes with a default ViewFactory (while the DefaultEditorKit
   * does not).
   */
  class CustomEditorKit extends StyledEditorKit {

    private static final long serialVersionUID = 1L;

    @Override
    public ViewFactory getViewFactory() {
      return new CustomViewFactory(super.getViewFactory());
    }
  }

  /**
   * Produces custom ParagraphViews, but uses the default ViewFactory for all
   * other elements.
   */
  class CustomViewFactory implements ViewFactory {

    private ViewFactory defaultViewFactory;

    CustomViewFactory(ViewFactory defaultViewFactory) {
      this.defaultViewFactory = defaultViewFactory;
    }

    @Override
    public View create(Element elem) {
      if (elem != null && elem.getName().equals(AbstractDocument.ParagraphElementName)) {
        return new CustomParagraphView(elem);
      }
      return defaultViewFactory.create(elem);
    }
  }

  /**
   * Paints a left hand child view with the line number for this Paragraph.
   */
  class CustomParagraphView extends ParagraphView {

    public final short MARGIN_WIDTH_PX = 25;

    private Element thisElement;

    private Font font;

    public CustomParagraphView(Element elem) {
      super(elem);
      thisElement = elem;
      this.setInsets((short) 0, (short) 0, (short) 0, (short) 0);
    }

    @Override
    protected void setInsets(short top, short left, short bottom, short right) {
      super.setInsets(top, (short) (left + MARGIN_WIDTH_PX), bottom, right);
    }

    @Override
    public void paintChild(Graphics g, Rectangle alloc, int index) {
      super.paintChild(g, alloc, index);

      // Allow of wrapped paragraph lines, but don't print redundant line
      // numbers.
      if (index > 0) {
        return;
      }

      // Pad left so the numbers align
      int lineNumber = getLineNumber() + 1;
      String lnStr = String.format("%3d", lineNumber);

      // Make sure we use a monospaced font.
      font = font != null ? font : new Font(Font.MONOSPACED, Font.PLAIN, getFont().getSize());
      g.setFont(font);

      int x = alloc.x - getLeftInset();
      int y = alloc.y + alloc.height - 3;
      g.drawString(lnStr, x, y);
    }

    private int getLineNumber() {
      // According to the Document.getRootElements() doc, there will "typically"
      // only be one root element.
      Element root = getDocument().getDefaultRootElement();
      int len = root.getElementCount();
      for (int i = 0; i < len; i++) {
        if (root.getElement(i) == thisElement) {
          return i;
        }
      }
      return 0;
    }
  }
}
