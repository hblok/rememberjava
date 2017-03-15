/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
package com.rememberjava.doc;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.Tag;
import com.sun.tools.javadoc.Main;

/**
 * Example self-contained Doclet which prints raw text.
 * 
 * @author Bob
 * @since 123
 * @custom Custom Annotation
 * @see "http://docs.oracle.com/javase/6/docs/technotes/guides/javadoc/doclet/overview.html"
 */
@Deprecated
public class SunDocletPrinter {

  public static String SOME_FIELD;

  /**
   * @see "http://docs.oracle.com/javase/7/docs/technotes/guides/javadoc/standard-doclet.html"
   */
  @SuppressWarnings(value = { "Test" })
  public static void main(String[] args) {
    System.out.println("- start main");
    Main.execute("MyName", SunDocletPrinter.class.getName(),
        new String[] { "com/rememberjava/doc/SunDocletPrinter.java" });
    System.out.println("- done execute");
  }

  /**
   * This method processes everything. And there's more to it.
   * 
   * @param root
   *          the root element
   * @return returns true
   */
  public static boolean start(RootDoc root) {
    System.out.println("--- start");

    for (ClassDoc classDoc : root.classes()) {
      System.out.println("Class: " + classDoc);

      // Class annotations
      for (AnnotationDesc annotation : classDoc.annotations()) {
        System.out.println("  Annotation: " + annotation);
      }

      // Class JavaDoc tags
      for (Tag tag : classDoc.tags()) {
        System.out.println("  Class tag:" + tag.name() + "=" + tag.text());
      }

      // Global constants and fields
      for (FieldDoc fieldDoc : classDoc.fields()) {
        System.out.println("  Field: " + fieldDoc);
      }

      // Methods
      for (MethodDoc methodDoc : classDoc.methods()) {
        System.out.println("  Method: " + methodDoc);

        // Method annotations
        for (AnnotationDesc annotation : methodDoc.annotations()) {
          System.out.println("    Annotation: " + annotation);
        }

        // Method JavaDoc comment (without parameters)
        System.out.println("    Doc: " + methodDoc.commentText());

        // Method JavaDoc (only the first sentence)
        for (Tag tag : methodDoc.firstSentenceTags()) {
          System.out.println("    Tag: " + tag);
        }

        // Method parameters (without return)
        for (ParamTag paramTag : methodDoc.paramTags()) {
          System.out.println("    Param:" + paramTag.parameterName() + "=" + paramTag.parameterComment());
        }

        // The full method JavaDoc text
        System.out.println("    Raw doc:\n" + methodDoc.getRawCommentText());
      }
    }

    System.out.println("--- the end");
    return true;
  }
}
