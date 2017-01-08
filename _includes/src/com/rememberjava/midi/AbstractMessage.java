package com.rememberjava.midi;

import javax.sound.midi.MidiMessage;

/**
 * Hold a {@link MidiMessage} and its timestamp, and deal with general constants
 * and parsing.
 * 
 * Helpful documentation about the MIDI format:
 * https://www.nyu.edu/classes/bello/FMT_files/9_MIDI_code.pdf
 *
 */
public abstract class AbstractMessage {

  public static final int STATUS_MASK = 0xF0;
  
  public static final int NOTE_OFF = 0x80;
  public static final int NOTE_ON = 0x90;
  public static final int CONTROL = 0xB0;
  public static final int SYSTEM = 0xF0;
  
  public static final int SYS_EXCLUSIVE = 0xF0;
  public static final int SYS_SONG_POSITION = 0xF2;
  public static final int SYS_SONG_SELECT = 0xF3;
  public static final int SYS_TIME_REQUEST = 0xF6;
  public static final int SYS_EOX = 0xF7;
  public static final int SYS_RT_TIMING_CLOCK = 0xF8;
  public static final int SYS_RT_START = 0xFA;
  public static final int SYS_RT_CONTINUE = 0xFB;
  public static final int SYS_RT_STOP = 0xFC;
  public static final int SYS_RT_ACTIVE_SENSING = 0xFE;
  public static final int SYS_RT_RESET = 0xFF;

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
    return data.length > 0 && (
        (data[0] & STATUS_MASK) == NOTE_ON ||
        (data[0] & STATUS_MASK) == NOTE_OFF);
  }
  
  public boolean noteOn() {
    return data.length > 0 && (data[0] & STATUS_MASK) == NOTE_ON;
  }
  
  public boolean noteOff() {
    return data.length > 0 && (data[0] & STATUS_MASK) == NOTE_OFF;
  }

  public boolean isControl() {
    return data.length > 0 && (data[0] & STATUS_MASK) == CONTROL;
  }
  
  public boolean isSystem() {
    return data.length > 0 && (data[0] & STATUS_MASK) == SYSTEM;
  }
  
  public boolean isSystemStart() {
    return data.length > 0 && midiByteToInt(data[0]) == SYS_RT_START;
  }
  
  public boolean isSystemStop() {
    return data.length > 0 && midiByteToInt(data[0]) == SYS_RT_STOP;
  }

  public boolean isSystemContinue() {
    return data.length > 0 && midiByteToInt(data[0]) == SYS_RT_CONTINUE;
  }
  
  public String getNote() {
    if (!isNote() || data.length != 3) {
      return null;
    }

    return NOTE_ORDER[data[1] % 12];
  }

  // TODO: move down
  // The note number to octave level is different between vendors.
  public int getOctave() {
    if (!isNote() || data.length != 3) {
      return -1;
    }

    return (data[1] / 12) - 1;
  }

  public String getNoteOctave() {
    return getNote() + getOctave();
  }
  
  public abstract String getControlName();
}
