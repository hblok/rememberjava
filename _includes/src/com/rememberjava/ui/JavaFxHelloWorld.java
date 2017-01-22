package com.rememberjava.ui;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class JavaFxHelloWorld extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("My App");

    Label label = new Label();

    Button button = new Button();
    button.setText("Say 'Hello World'");
    button.setOnAction((e) -> {
      label.setText("Hi!");
    });

    VBox root = new VBox();
    ObservableList<Node> children = root.getChildren();
    children.add(button);
    children.add(label);

    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }
}
