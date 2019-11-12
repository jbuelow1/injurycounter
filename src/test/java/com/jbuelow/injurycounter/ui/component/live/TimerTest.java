package com.jbuelow.injurycounter.ui.component.live;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import org.junit.jupiter.api.Test;

class TimerTest {

  @Test
  void getTime10sec() {
    Timer timer = new Timer();
    timer.setLastInjury(Instant.now().minusSeconds(10));
    String out = timer.getHumanReadableTimeSinceInstant();
    assertEquals(out, "10 seconds");
  }

  @Test
  void getTime10min10sec() {
    Timer timer = new Timer();
    timer.setLastInjury(Instant.now().minusSeconds(10).minusSeconds(600));
    String out = timer.getHumanReadableTimeSinceInstant();
    assertEquals(out, "10 minutes, 10 seconds");
  }

  @Test
  void getTime1hour10min10sec() {
    Timer timer = new Timer();
    timer.setLastInjury(Instant.now().minusSeconds(10).minusSeconds(600).minusSeconds(3600000));
    String out = timer.getHumanReadableTimeSinceInstant();
    assertEquals(out, "1 hours, 10 minutes, 10 seconds");
  }

  @Test
  void getTime1day1hour10min10sec() {
    Timer timer = new Timer();
    timer.setLastInjury(Instant.now().minusSeconds(10).minusSeconds(600).minusSeconds(3600000).minusSeconds(86400000));
    String out = timer.getHumanReadableTimeSinceInstant();
    assertEquals(out, "1 hours, 10 minutes, 10 seconds");
  }

}