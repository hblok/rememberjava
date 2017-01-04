package com.rememberjava.midi;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiSystem;

/**
 * Helper methods for the javax.sound.midi package.
 */
public class MidiUtils {

  /**
   * Returns {@link Info} representations for all available MIDI devices as a
   * stream.
   */
  public static Stream<Info> deviceInfoStream() {
    return Arrays.asList(MidiSystem.getMidiDeviceInfo()).stream();
  }

  /**
   * Returns a {@link Stream} of {@link MidiDevice}s filtered by their names, as
   * reported by their respective Info objects.
   * 
   * @param deviceName
   *          the {@link Info#getName()} must be equal to or contain this
   *          sub-string to be included.
   * @return a Stream of MidiDevice.
   */
  public static Stream<MidiDevice> filteredDeviceStream(String deviceName) {
    return deviceInfoStream()
        .filter(onInfoNameContains(deviceName))
        .map(MidiUtils::getDevice);
  }

  /**
   * Creates a filter predicate on the name of an {@link Info} object. The name
   * must contain the specified string.
   * 
   * @param str
   *          the name or sub-string of the name to filter on.
   * @return a {@link Predicate} which can be used in a
   *         {@link Stream#filter(Predicate)} method.
   */
  public static Predicate<? super Info> onInfoNameContains(String str) {
    return info -> info.getName().contains(str);
  }

  /**
   * Creates a filter predicate on the simple class name (without package name)
   * of the MidiDevice.
   * 
   * @param str
   *          the class name (file name) to filter on.
   * @return a {@link Predicate} which can be used in a
   *         {@link Stream#filter(Predicate)} method.
   */
  public static Predicate<? super MidiDevice> onClassnameEquals(String str) {
    return device -> device.getClass().getSimpleName().equals(str);
  }

  /**
   * Returns the MidiDevice for the specified Info object. All Exceptions are
   * ignored, and null is returned if one is thrown while getting the device.
   * 
   * @param info
   *          the Info representation as returned from
   *          {@link MidiSystem#getMidiDeviceInfo}.
   * @return the requested device, or null if the device could not be obtained.
   */
  public static MidiDevice getDevice(Info info) {
    try {
      return MidiSystem.getMidiDevice(info);
    } catch (Exception e) {
      return null;
    }
  }

}
