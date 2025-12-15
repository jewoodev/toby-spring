package tobyspring.hellospring.learningtest;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class ClockTest {
    @Test
    void clock() throws InterruptedException {
        Clock clock = Clock.systemDefaultZone();

        LocalDateTime dt1 = LocalDateTime.now(clock);
        TimeUnit.SECONDS.sleep(1);
        LocalDateTime dt2 = LocalDateTime.now(clock);

        assertThat(dt1).isBefore(dt2);
    }

    @Test
    void fixedClock() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        LocalDateTime dt1 = LocalDateTime.now(clock);
        LocalDateTime dt2 = LocalDateTime.now(clock);
        LocalDateTime dt3 = LocalDateTime.now(clock).plusHours(1);

        assertThat(dt1).isEqualTo(dt2);
        assertThat(dt3).isEqualTo(dt2.plusHours(1));
    }
}
