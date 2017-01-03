package com.rememberjava.midi;

import java.util.Arrays;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;

import org.junit.Test;


/**
 * See also: https://docs.oracle.com/javase/tutorial/sound/overview-MIDI.html
 */

public class MidiApiTest {

  private static final String SOFT_SYNTHESIZER = "SoftSynthesizer";

  @Test
  public void testGetMidiDeviceInfo() throws MidiUnavailableException {
    Info[] infos = MidiSystem.getMidiDeviceInfo();
    for (Info info : infos) {
      MidiDevice device = MidiSystem.getMidiDevice(info);
      System.out.println("Info: " + device + ", '" + info.getName() + "',  '" + info.getVendor() + "', '"
          + info.getVersion() + "', '" + info.getDescription() + "'");
    }
  }

  @Test
  public void testGetSequencer() throws MidiUnavailableException {
    Sequencer sequencer = MidiSystem.getSequencer();
    Transmitter transmitter = sequencer.getTransmitter();

    System.out.println("Sequencer: " + sequencer.getDeviceInfo());
    System.out.println("Transmitter: " + transmitter);
  }

  @Test
  public void testGetSynthesizer() throws MidiUnavailableException {
    Synthesizer synthesizer = MidiSystem.getSynthesizer();
    Receiver receiver = synthesizer.getReceiver();

    System.out.println("Synthesizer: " + synthesizer.getDeviceInfo());
    System.out.println("Receiver: " + receiver);
  }

  @Test
  public void testPlayNote() throws InvalidMidiDataException, MidiUnavailableException {
    ShortMessage msg = new ShortMessage();

    // 60 = middle C, C4 (or C3 or C5)
    // https://en.wikipedia.org/wiki/Scientific_pitch_notation#Similar_systems
    int channel = 0;
    int note = 60;
    int velocity = 127; // velocity (i.e. volume); 127 = high
    msg.setMessage(ShortMessage.NOTE_ON, channel, note, velocity);

    long timeStamp = -1;
    MidiDevice synthesizer = getSynthesizer("Gervill");
    synthesizer.open();
    Receiver receiver = synthesizer.getReceiver();
    receiver.send(msg, timeStamp);
    receiver.close();
  }
  
  private MidiDevice getSynthesizer(String deviceName) throws MidiUnavailableException {
     return Arrays.asList(MidiSystem.getMidiDeviceInfo())
         .stream()
         .filter(info -> info.getName().equals(deviceName))
         .map(info -> getDevice(info))
         .filter(device -> device.getClass().getSimpleName().equals(SOFT_SYNTHESIZER))
         .findFirst().get();
  }

  private MidiDevice getDevice(Info info) {
    try {
      return MidiSystem.getMidiDevice(info);
    } catch (MidiUnavailableException e) {
      return null;
    }
  }
}
