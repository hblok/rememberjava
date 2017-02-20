/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
package com.rememberjava.midi.roland;

import java.util.Map;

import javax.sound.midi.MidiMessage;

public class Tb03Message extends RolandMessage {

  public enum Tb03Controllers implements Controllers {
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

    private Tb03Controllers(int id) {
      this.id = id;
    }

    public int getId() {
      return id;
    }
  }

  private static Map<Integer, String> ID_NAME_MAP = createIdMap(Tb03Controllers.values());

  public Tb03Message(MidiMessage msg, long timestamp) {
    super(msg, timestamp);
  }

  protected Map<Integer, String> getIdMap() {
    return ID_NAME_MAP;
  }

}
