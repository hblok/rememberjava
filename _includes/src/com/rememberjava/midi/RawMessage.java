package com.rememberjava.midi;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;

public class RawMessage extends AbstractMessage {

  public RawMessage(MidiMessage msg, long timestamp) {
    super(msg, timestamp);
  }
  
  public RawMessage(MidiEvent event) {
    super(event.getMessage(), event.getTick());
  }

  @Override
  public String getControlName() {
    return null;
  }
}
