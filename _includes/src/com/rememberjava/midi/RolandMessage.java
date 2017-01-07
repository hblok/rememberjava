package com.rememberjava.midi;

import javax.sound.midi.MidiMessage;

public abstract class RolandMessage extends AbstractMessage {

  public RolandMessage(MidiMessage msg, long timestamp) {
    super(msg, timestamp);
  }
  
  
}
