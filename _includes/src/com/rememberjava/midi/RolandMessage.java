package com.rememberjava.midi;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sound.midi.MidiMessage;

public abstract class RolandMessage extends AbstractMessage {

  protected static interface Controllers {
    public int getId();

    public String name();
  }

  public RolandMessage(MidiMessage msg, long timestamp) {
    super(msg, timestamp);
  }

  protected static Map<Integer, String> createIdMap(Controllers... values) {
    return Arrays.asList(values).stream()
        .collect(Collectors.toMap(c -> c.getId(), c -> c.name()));
  }

  protected abstract Map<Integer, String> getIdMap();
  
  @Override
  public String getControlName() {
    if (!isControl() || data.length != 3) {
      return null;
    }

    int id = (data[1] & 0xFF);
    if (getIdMap().containsKey(id)) {
      return getIdMap().get(id) + " " + id;
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