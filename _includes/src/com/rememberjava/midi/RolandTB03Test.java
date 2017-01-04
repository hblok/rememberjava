package com.rememberjava.midi;

import static com.rememberjava.midi.MidiUtils.filteredDeviceStream;
import static com.rememberjava.midi.MidiUtils.onClassnameEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Transmitter;

import org.junit.After;
import org.junit.Test;

public class RolandTB03Test {

  // Roland Boutique
  private static final String BOUTIQUE = "Boutique";
  
  private static final String MIDI_IN_DEVICE = "MidiInDevice";

  private MidiDevice in;

  @After
  public void tearDow() {
    if (in != null) {
      in.close();
    }
  }

  @Test
  public void findDevice() {
    List<String> deviceClassnames = filteredDeviceStream(BOUTIQUE)
        .map(device -> device.getClass().getSimpleName())
        .collect(Collectors.toList());

    assertTrue(deviceClassnames.contains(MIDI_IN_DEVICE));
    assertTrue(deviceClassnames.contains("MidiOutDevice"));
  }

  @Test
  public void clockSignal() throws MidiUnavailableException, InterruptedException {
    in = getMidiIn();
    in.open();

    int transmitters = in.getMaxTransmitters();
    System.out.println("transmitters:" + transmitters);
    assertTrue(transmitters != 0);

    long timestamp = in.getMicrosecondPosition();
    System.out.println("init timestamp=" + timestamp);
    assertTrue(timestamp > -1);

    Transmitter transmitter = in.getTransmitter();
    SpyReceiver spy = new SpyReceiver();
    transmitter.setReceiver(spy);

    Thread.sleep(50);
    long lastTimeStamp = spy.getLastTimeStamp();
    System.out.println("lastTimeStamp=" + lastTimeStamp);
    System.out.println("lastMessage=" + spy.getLastMessage());
    assertTrue(lastTimeStamp > -1);

    in.close();
  }
  
  private MidiDevice getMidiIn() throws MidiUnavailableException {
    return filteredDeviceStream(BOUTIQUE)
        .filter(onClassnameEquals(MIDI_IN_DEVICE))
        .findFirst().get();
 }
}
