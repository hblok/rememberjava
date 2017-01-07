package com.rememberjava.midi;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sound.midi.MidiMessage;

public class Tb03Message extends RolandMessage {

  public enum Controllers {
    TUNING(0x68), 
    CUT_OFF_FREQ(0x4A), 
    RESONANCE(0x47), 
    ENV_MOD(0x0C), 
    DECAY(0x4B), 
    ACCENT(0x10), 
    OVERDRIVE(0x11), 
    TIME(0x12), 
    FEEDBACK(0x13),
    
    SLIDE(0x66);
    
    private int id;

    private Controllers(int id) {
      this.id = id;
    }

    public int getId() {
      return id;
    }
  }

  private static Map<Integer, String> ID_NAME_MAP = 
      Arrays.asList(Controllers.values()).stream()
        .collect(Collectors.toMap(c -> c.getId(), c -> c.name()));

  public Tb03Message(MidiMessage msg, long timestamp) {
    super(msg, timestamp);
  }

  @Override
  public String getControlName() {
    if (!isControl() || data.length != 3) {
      return null;
    }

    int id = (data[1] & 0xFF);
    if (ID_NAME_MAP.containsKey(id)) {
      return ID_NAME_MAP.get(id);
    }

    return String.format("Unknown controller (%d)", id);
  }

  @Override
  public String toString() {
    if (isNote()) {
      return getNoteOctave();
    } else if (isControl()) {
      return getControlName();
    }

    return "UNKNOWN";
  }
}
