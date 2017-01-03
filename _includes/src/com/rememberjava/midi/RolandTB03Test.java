package com.rememberjava.midi;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

public class RolandTB03Test {

  // Roland Boutique
  private static final String BOUTIQUE = "Boutique";

  @Test
  public void findDevice() {
    List<String> deviceClassnames = Arrays.asList(MidiSystem.getMidiDeviceInfo())
        .stream()
        .filter(info -> info.getName().contains(BOUTIQUE))
        .map(info -> getDevice(info))
        .map(device -> device.getClass().getSimpleName())
        .collect(Collectors.toList());

    assertTrue(deviceClassnames.contains("MidiInDevice"));
    assertTrue(deviceClassnames.contains("MidiOutDevice"));
  }

  private MidiDevice getDevice(Info info) {
    try {
      return MidiSystem.getMidiDevice(info);
    } catch (MidiUnavailableException e) {
      return null;
    }
  }

}
