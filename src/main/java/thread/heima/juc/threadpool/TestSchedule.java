package thread.heima.juc.threadpool;

import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TestSchedule {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        LocalDateTime time = now.withHour(18).withMinute(20).withSecond(0).withNano(0).with(DayOfWeek.THURSDAY);
        if(now.compareTo(time) > 0){
            time.plusWeeks(1);
        }

        // System.out.println(time);
        long duration = Duration.between(now, time).toMillis();

        long peroid = 1000 * 60 * 60 * 24 * 7;

        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        pool.scheduleAtFixedRate(() -> {
            log.debug("running");
        }, duration, peroid, TimeUnit.MILLISECONDS);
    }
}
