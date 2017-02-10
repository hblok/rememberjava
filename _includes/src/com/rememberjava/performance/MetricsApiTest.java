package com.rememberjava.performance;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.codahale.metrics.Timer.Context;

public class MetricsApiTest {

  private MetricRegistry metrics;

  @Before
  public void setup() {
    metrics = new MetricRegistry();
  }

  @After
  public void report() {
    ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).build();
    reporter.report();
  }

  @Test
  public void testMeter() throws InterruptedException {
    Meter meter = metrics.meter("meter");
    meter.mark();
    Thread.sleep(100);
    meter.mark();
  }

  @Test
  public void testGauge() {
    Gauge<Integer> g = (() -> 123);
    metrics.register("gauge", g);
  }

  @Test
  public void testCounter() {
    Counter counter = metrics.counter("counter");
    counter.inc();
    counter.inc(5);
  }

  @Test
  public void testHistogram() {
    Histogram histogram = metrics.histogram("histogram");
    histogram.update(1);
    histogram.update(3);
    histogram.update(3);
    histogram.update(3);
  }

  @Test
  public void testTimer() throws InterruptedException {
    Timer timer = metrics.timer("timer");
    Context time = timer.time();
    Thread.sleep(200);
    time.stop();
  }
}
