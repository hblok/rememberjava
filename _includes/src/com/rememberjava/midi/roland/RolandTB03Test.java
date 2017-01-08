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
import javax.sound.midi.Transmitter;

import org.junit.After;
import org.junit.Test;

import com.rememberjava.midi.AbstractMessage;
import com.rememberjava.midi.RawMessage;
import com.rememberjava.midi.ReceiverLimitedQueue;
import com.rememberjava.midi.SpyReceiver;

public class RolandTB03Test {

  // Roland Boutique
  private static final String BOUTIQUE = "Boutique";

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
    assertTrue(deviceClassnames.contains(MIDI_OUT_DEVICE));
  }

  @Test
  public void clockSignal() throws MidiUnavailableException, InterruptedException {
    in = getMidiIn(BOUTIQUE);
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
  
  @Test
  public void testMessageQueue() throws MidiUnavailableException {
    in = getMidiIn(BOUTIQUE);
    in.open();

    ReceiverLimitedQueue<RawMessage> queue = new ReceiverLimitedQueue<>(RawMessage::new, 50);
    in.getTransmitter().setReceiver(queue);
    
    queue.eternalStream()
      .filter(m -> m != null)
      .limit(20)
      .forEach(this::printMessage);
    
    in.close();
  }
  
  @Test
  public void testNotes() throws MidiUnavailableException {
    ReceiverLimitedQueue<Tb03Message> queue = createTb03Queue();

    queue.eternalStream()
      .filter(m -> m != null)
      .limit(50)
      .filter(m -> m.isNote())
      .map(m -> m.getNoteOctave())
      .forEach(System.out::println);
  }
  
  @Test
  public void testControllers() throws MidiUnavailableException {
    ReceiverLimitedQueue<Tb03Message> queue = createTb03Queue();

    queue.eternalStream()
      .filter(m -> m != null)
      .limit(20)
      .filter(m -> m.isControl())
      .map(m -> m.getControlName())
      .forEach(System.out::println);
  }
  
  @Test
  public void testPattern() throws MidiUnavailableException {
    ReceiverLimitedQueue<Tb03Message> queue = createTb03Queue();

    queue.eternalStream()
      .filter(m -> m != null)
      .limit(20)
      .filter(m -> !m.isSystem())
      .forEach(System.out::println);
  }

  private ReceiverLimitedQueue<Tb03Message> createTb03Queue() throws MidiUnavailableException {
    in = getMidiIn(BOUTIQUE);
    in.open();

    ReceiverLimitedQueue<Tb03Message> queue = new ReceiverLimitedQueue<>(Tb03Message::new, 20);
    in.getTransmitter().setReceiver(queue);
    
    return queue;
  }

  private void printMessage(AbstractMessage msg) {
    System.out.printf("%d: ", msg.getTimestamp());
    for (byte b : msg.getRawData()) {
      System.out.print(AbstractMessage.midiByteToBinary(b) + " ");
    }
    System.out.println(msg.getNoteOctave());
  }
}
