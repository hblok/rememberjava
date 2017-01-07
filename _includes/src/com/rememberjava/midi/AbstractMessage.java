package com.rememberjava.midi;

import javax.sound.midi.MidiMessage;

public abstract class AbstractMessage {

  public static final int NOTE_OFF = 0x80;
  public static final int NOTE_ON = 0x90;
  public static final int CONTROL = 0xB0;
  public static final int SYSTEM = 0xF0;

  private static final String[] NOTE_ORDER = {
      "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };


  private final MidiMessage msg;
  private final long timestamp;
  protected final byte[] data;

  protected AbstractMessage(MidiMessage msg, long timestamp) {
    this.msg = msg;
    this.timestamp = timestamp;
    this.data = msg.getMessage();
  }

  public MidiMessage getMidiMessage() {
    return msg;
  }

  public byte[] getRawData() {
    return msg.getMessage();
  }

  public long getTimestamp() {
    return timestamp;
  }

  public static int midiByteToInt(byte b) {
    return (int) (b & 0xFF);
  }

  public static String midiByteToBinary(byte b) {
    return String.format("%8s", Integer.toBinaryString(midiByteToInt(b)));
  }

  public static String midiByteToHex(byte b) {
    return Integer.toHexString(midiByteToInt(b));
  }

  public boolean isNote() {
    return (data[0] & NOTE_ON) == 0 || (data[0] & NOTE_OFF) == 0;
  }

  public String getNote() {
    if (!isNote() && data.length != 3) {
      return null;
    }

    return NOTE_ORDER[data[1] % 12];
  }

  // TODO: move down
  public int getOctave() {
    if (!isNote() && data.length != 3) {
      return -1;
    }

    return (data[1] / 12) - 1;
  }

  public String getNoteOctave() {
    return getNote() + getOctave();
  }
}
