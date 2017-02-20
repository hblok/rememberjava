/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
package com.rememberjava.midi.roland.tr8;

import static com.rememberjava.midi.MidiUtils.getMidiIn;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;

import com.rememberjava.midi.ReceiverLimitedQueue;
import com.rememberjava.midi.roland.RolandUtils;
import com.rememberjava.midi.roland.Tb03Message;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class Tr8SequenceRecorder extends Application {

  private Tr8Ui ui;

  private static MidiDevice in;

  public static void main(String[] args) {
    try {
      launch(args);
    } finally {
      in.close();
    }
  }

  @Override
  public void start(Stage primaryStage) throws MidiUnavailableException {
    initUi(primaryStage);
    
    new Thread(() -> {
      initMidi();
    }).start();
  }

  private void initUi(Stage primaryStage) {
    ui = new Tr8Ui();
    ui.init();

    primaryStage.setScene(new Scene(ui));
    primaryStage.show();
  }

  private void initMidi() {
    try {
      in = getMidiIn(RolandUtils.BOUTIQUE);
      
      in.open();
      
      ReceiverLimitedQueue<Tr8Message> queue = new ReceiverLimitedQueue<>(Tr8Message::new, 50);
      in.getTransmitter().setReceiver(queue);

      queue.eternalStream()
        .filter(m -> m != null)
        .filter(m -> (m.isControl()))
        .forEach(ui::recieve);
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    } finally {
      in.close();
    }
  }

}
