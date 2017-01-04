package com.rememberjava.midi;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

/**
 * A unit test helper class to spy on {@link MidiMessage}s from a
 * {@link Transmitter}. The last message and timestamp is recorded and get be
 * retrieved.
 */
public class SpyReceiver implements Receiver {

  private MidiMessage lastMessage;
  private long lastTimeStamp = -1;

  @Override
  public void send(MidiMessage message, long timeStamp) {
    lastMessage = message;
    lastTimeStamp = timeStamp;
  }

  @Override
  public void close() {
  }

  long getLastTimeStamp() {
    return lastTimeStamp;
  }
  
  MidiMessage getLastMessage() {
    return lastMessage;
  }
}