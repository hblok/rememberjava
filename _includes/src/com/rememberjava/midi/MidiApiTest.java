package com.rememberjava.midi;

import static com.rememberjava.midi.MidiUtils.filteredDeviceStream;
import static com.rememberjava.midi.MidiUtils.onClassnameEquals;

import java.io.File;
import java.io.IOException;
import java.util.stream.IntStream;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiFileFormat;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;

import org.junit.Test;


/**
 * See also: https://docs.oracle.com/javase/tutorial/sound/overview-MIDI.html
 */
public class MidiApiTest {

  private static final String SOFT_SYNTHESIZER = "SoftSynthesizer";
  
  private static final String MIDI_FILE = "test.mid";

  @Test
  public void testGetMidiDeviceInfo() throws MidiUnavailableException {
    Info[] infos = MidiSystem.getMidiDeviceInfo();
    for (Info info : infos) {
      MidiDevice device = MidiSystem.getMidiDevice(info);
      boolean isSequencer = device instanceof Sequencer;
      boolean isSynthesizer = device instanceof Synthesizer;

      System.out.println(
          "Info: " + device + ", '" + info.getName() + "',  '" + info.getVendor() + "', '" + info.getVersion() + "', '"
              + info.getDescription() + "', " + "sequencer=" + isSequencer + ", synthesizer=" + isSynthesizer);
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
     return filteredDeviceStream(deviceName)
         .filter(onClassnameEquals(SOFT_SYNTHESIZER))
         .findFirst().get();
  }
  
  @Test
  public void testMidiFileFormat() throws InvalidMidiDataException, IOException {
    File file = new File(MIDI_FILE);
    MidiFileFormat format = MidiSystem.getMidiFileFormat(file);

    System.out.println("bytes: " + format.getByteLength());
    System.out.println("length ms: " + format.getMicrosecondLength());
    System.out.println("divisionType: " + format.getDivisionType());
    System.out.println("resolution: " + format.getResolution());
    System.out.println("type: " + format.getType());
    System.out.println("properties: " + format.properties().size());

    format.properties().entrySet().stream()
      .map(e -> e.getValue() + "=" + e.getKey())
      .forEach(System.out::println);
  }
  
  @Test
  public void testReadFile() throws InvalidMidiDataException, IOException {
    File file = new File(MIDI_FILE);
    Sequence sequence = MidiSystem.getSequence(file);
    
    System.out.println("tracks: " + sequence.getTracks().length);
    System.out.println("patches: " + sequence.getPatchList().length);
    
    for (Track track : sequence.getTracks()) {
      int size = track.size();
      System.out.println("track size: "+size);
      
      IntStream.range(0, size).boxed()
        .map(track::get)
        .map(RawMessage::new)
        .map(AbstractMessage::getNoteOctave)
        .map(str -> str + " ")
        .forEach(System.out::print);
      System.out.println();
    }
  }
}
