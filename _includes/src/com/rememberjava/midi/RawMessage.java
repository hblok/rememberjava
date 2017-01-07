package com.rememberjava.midi;

import javax.sound.midi.MidiMessage;

public class RawMessage extends AbstractMessage {

  public RawMessage(MidiMessage msg, long timestamp) {
    super(msg, timestamp);
  }
}
