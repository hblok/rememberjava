package com.rememberjava.midi.roland.tr8;

import static com.rememberjava.midi.roland.tr8.InstrumentKnob.*;

import java.util.Arrays;
import java.util.List;

public enum Instrument {
  BASS_DRUM(TUNE, COMP, ATTACK, DECAY),
  SNARE_DRUM(TUNE, COMP, SNAPPY, DECAY),
  LOW_TOM, MID_TOM, HIGH_TOM,
  RIM_SHOT, HAND_CLAP,
  CLOSED_HIHAT, OPEN_HIHAT,
  CRASH_CYMBAL, RIDE_CYMBAL;

  private final List<InstrumentKnob> knobs;
  
  private final String formattedName;
  
  private final String abbreviation;
  
  private Instrument() {
    this(TUNE, DECAY);
  }

  private Instrument(InstrumentKnob... knobs) {
    this.knobs = Arrays.asList(knobs);
    formattedName = format();
    abbreviation = abbreviate();
  }

  public List<InstrumentKnob> getKnobs() {
    return knobs;
  }

  public String getFormattedName() {
    return formattedName;
  }

  public String getAbbreviation() {
    return abbreviation;
  }

  private String format() {
    String[] split = split();
    return captialize(split[0]) + " " + captialize(split[1]);
  }

  private String[] split() {
    return name().split("_");
  }

  private String captialize(String str) {
    return str.charAt(0) + str.substring(1).toLowerCase();
  }

  private String abbreviate() {
    String[] split = split();
    return "" + split[0].charAt(0) + split[1].charAt(0);
  }

  public String getMidiName(InstrumentKnob knob) {
    return getAbbreviation() + "_" + knob.name();
  }
}
