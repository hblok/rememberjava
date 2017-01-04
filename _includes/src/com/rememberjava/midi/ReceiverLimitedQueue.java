package com.rememberjava.midi;

import java.util.stream.Stream;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

import org.apache.commons.collections4.queue.CircularFifoQueue;

public class ReceiverLimitedQueue implements Receiver {

  private CircularFifoQueue<MidiMessage> queue;

  public ReceiverLimitedQueue(int size) {
    queue = new CircularFifoQueue<>(size);
  }

  @Override
  public void send(MidiMessage message, long timeStamp) {
    synchronized (queue) {
      queue.offer(message);
    }
  }

  /**
   * Polls the queue for the oldest message.
   * 
   * @return returns the oldest message on the queue, or null if the queue is
   *         empty.
   */
  public MidiMessage poll() {
    synchronized (queue) {
      return queue.poll();
    }
  }

  /**
   * Returns a never ending stream of {@link MidiMessage}s.
   * 
   * @return a Stream of MidiMessages.
   */
  public Stream<MidiMessage> eternalStream() {
    return Stream.generate(this::poll);
  }

  @Override
  public void close() {
  }
}
