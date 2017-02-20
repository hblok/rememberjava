/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
package com.rememberjava.midi;

import java.util.function.BiFunction;
import java.util.stream.Stream;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

import org.apache.commons.collections4.queue.CircularFifoQueue;

public class ReceiverLimitedQueue<T extends AbstractMessage> implements Receiver {

  @FunctionalInterface
  public interface MessageConstructor<T> extends BiFunction<MidiMessage, Long, T> {}

  private final MessageConstructor<T> constructor;

  private final CircularFifoQueue<T> queue;

  public ReceiverLimitedQueue(MessageConstructor<T> constructor, int size) {
    this.constructor = constructor;
    queue = new CircularFifoQueue<>(size);
  }

  @Override
  public void send(MidiMessage message, long timeStamp) {
    synchronized (queue) {
      queue.offer(constructor.apply(message, timeStamp));
    }
  }

  /**
   * Polls the queue for the oldest message.
   * 
   * @return returns the oldest message on the queue, or null if the queue is
   *         empty.
   */
  public T poll() {
    synchronized (queue) {
      return queue.poll();
    }
  }

  /**
   * Returns a never ending stream of {@link MidiMessage}s.
   * 
   * @return a Stream of MidiMessages.
   */
  public Stream<T> eternalStream() {
    return Stream.generate(this::poll);
  }

  @Override
  public void close() {}
}
