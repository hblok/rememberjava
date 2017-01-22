package com.rememberjava.midi.roland;

import static com.rememberjava.midi.MidiUtils.MIDI_IN_DEVICE;
import static com.rememberjava.midi.MidiUtils.MIDI_OUT_DEVICE;
import static com.rememberjava.midi.MidiUtils.filteredDeviceStream;
import static com.rememberjava.midi.MidiUtils.getMidiIn;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;

import org.junit.After;
import org.junit.Test;

import com.rememberjava.midi.ReceiverLimitedQueue;
import com.rememberjava.midi.roland.tr8.Tr8Message;

public class RolandTR8Test {

  private MidiDevice in;

  @After
  public void tearDow() {
    if (in != null) {
      in.close();
    }
  }

  @Test
  public void findDevice() {
    List<String> deviceClassnames = filteredDeviceStream(RolandUtils.TR8)
        .map(device -> device.getClass().getSimpleName())
        .collect(Collectors.toList());

    assertTrue(deviceClassnames.contains(MIDI_IN_DEVICE));
    assertTrue(deviceClassnames.contains(MIDI_OUT_DEVICE));
  }
  

  @Test
  public void testControllers() throws MidiUnavailableException {
    ReceiverLimitedQueue<Tr8Message> queue = createTr8Queue();

    queue.eternalStream()
      .filter(m -> m != null)
      .limit(2000)
      .filter(m -> m.isControl())
      .map(m -> m.getControlName())
      .forEach(System.out::println);
  }
  
  private ReceiverLimitedQueue<Tr8Message> createTr8Queue() throws MidiUnavailableException {
    in = getMidiIn(RolandUtils.TR8);
    System.out.println("Found device: " + in.getDeviceInfo().getName());
    in.open();

    ReceiverLimitedQueue<Tr8Message> queue = new ReceiverLimitedQueue<>(Tr8Message::new, 50);
    in.getTransmitter().setReceiver(queue);
    
    return queue;
  }

}
