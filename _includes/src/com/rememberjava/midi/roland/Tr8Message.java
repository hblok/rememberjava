package com.rememberjava.midi.roland;

import java.util.Map;

import javax.sound.midi.MidiMessage;

public class Tr8Message extends RolandMessage {

  /**
   * With help from
   * http://cdm.link/2014/04/aira-secrets-get-midi-control-sequence-externally-download-free-ableton-m4l-patches
   */
  private enum Tr8Controllers implements Controllers {
    ACCENT(0x47),
    SHUFFLE(0x09),

    REVERB_LEVEL(0x5B),
    REVERB_TIME(0x59),
    REVERB_GATE(0x5A),

    DELAY_LEVEL(0x10),
    DELAY_TIME(0x11),
    DELAY_FEEDBACK(0x12),

    EXT_IN_LEVEL(0xC),
    EXT_IN_SIDE_CHAIN(0xD),

    BD_TUNE(0x14),
    BD_ATTACK(0x15),
    BD_COMP(0x16),
    BD_DECAY(0x17),
    BD_VOLUME(0x18),

    SD_TUNE(0x19),
    SD_SNAPPY(0x1A),
    SD_COMP(0x1B),
    SD_DECAY(0x1C),
    SD_VOLUME(0x1D),

    LT_TUNE(0x2E),
    LT_DECAY(0x2F),
    LT_VOLUME(0x30),

    MT_TUNE(0x31),
    MT_DECAY(0x32),
    MT_VOLUME(0x33),

    HT_TUNE(0x34),
    HT_DECAY(0x35),
    HT_VOLUME(0x36),

    RS_TUNE(0x37),
    RS_DECAY(0x38),
    RS_VOLUME(0x39),

    HC_TUNE(0x3A),
    HC_DECAY(0x3B),
    HC_VOLUME(0x3C),

    CH_TUNE(0x3D),
    CH_DECAY(0x3E),
    CH_VOLUME(0x3F),

    OH_TUNE(0x50),
    OH_DECAY(0x51),
    OH_VOLUME(0x52),

    CC_TUNE(0x53),
    CC_DECAY(0x54),
    CC_VOLUME(0x55),

    RC_TUNE(0x56),
    RC_DECAY(0x57),
    RC_VOLUME(0x58);

    private int id;

    private Tr8Controllers(int id) {
      this.id = id;
    }

    public int getId() {
      return id;
    }
  }

  private static Map<Integer, String> ID_NAME_MAP = createIdMap(Tr8Controllers.values());

  public Tr8Message(MidiMessage msg, long timestamp) {
    super(msg, timestamp);
  }

  protected Map<Integer, String> getIdMap() {
    return ID_NAME_MAP;
  }
}
