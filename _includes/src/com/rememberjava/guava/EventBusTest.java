/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
package com.rememberjava.guava;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class EventBusTest {

  class Player {
    boolean playing;
    boolean paused;

    @Subscribe
    void play(PlayEvent e) {
      System.out.println("play");
      playing = true;
      paused = false;
    }

    @Subscribe
    void pause(PauseEvent e) {
      System.out.println("pause");
      playing = false;
      paused = true;
    }

    @Subscribe
    void stop(StopEvent e) {
      System.out.println("stop");
      playing = false;
      paused = false;
    }
  }

  class PlayEvent {}

  class PauseEvent {}

  class StopEvent {}

  private Player player;
  private EventBus bus;

  @Before
  public void setup() {
    player = new Player();

    bus = new EventBus();
    bus.register(player);
  }

  @Test
  public void testPlay() {
    bus.post(new PlayEvent());

    assertTrue(player.playing);
    assertFalse(player.paused);
  }

  @Test
  public void testAll() {
    bus.post(new Object());

    assertFalse(player.playing);
    assertFalse(player.paused);
  }
}
