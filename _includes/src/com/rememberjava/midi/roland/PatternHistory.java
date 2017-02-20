/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
package com.rememberjava.midi.roland;

import static com.rememberjava.midi.MidiUtils.getMidiIn;

import java.util.function.Consumer;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;

import com.rememberjava.midi.MidiUtils;
import com.rememberjava.midi.ReceiverLimitedQueue;

public class PatternHistory {

  private MidiDevice in;
  
  private int noteIndex;

  public PatternHistory() {

  }

  public static void main(String[] args) {
    PatternHistory hist = new PatternHistory();
    try {
      hist.start();
      hist.loop();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      hist.close();
      System.out.println("Closed device");
    }
  }

  private void close() {
    in.close();
  }

  private void start() throws MidiUnavailableException {
    in = getMidiIn(RolandUtils.BOUTIQUE);
    in.open();
    System.out.println("Device open: " + in.getDeviceInfo());
  }

  private void loop() throws MidiUnavailableException {
    ReceiverLimitedQueue<Tb03Message> queue = new ReceiverLimitedQueue<>(Tb03Message::new, 50);
    in.getTransmitter().setReceiver(queue);

    queue.eternalStream()
      .filter(m -> m != null)
      .filter(m -> (m.noteOn() || m.isSystem()))
      .forEach(this::printNote);
  }

  private void printNote(Tb03Message msg) {
    if(msg.isSystemStart() || msg.isSystemContinue()) {
      System.out.println(".");
      noteIndex = 0;
      return;
    } else if (msg.isSystemStop()) {
      System.out.println("\nStop");
      return;
    } else if (msg.isSystem()) {
      return;
    }
    
    System.out.printf("%3s ", msg.getNoteOctave());
    noteIndex++;
    if (noteIndex % 4 == 0) {
      System.out.print(" | ");
    }
    if (noteIndex % 16 == 0) {
      System.out.println("");
    }
  }

}
