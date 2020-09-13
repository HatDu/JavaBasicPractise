package thread.heima.juc.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
@Slf4j
public class TestShutdown {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(1, new ThreadFactory() {
            private AtomicInteger t = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "pool_t" + t.getAndIncrement());
            }
        });

        Future<String> future1 = pool.submit(() -> {
            log.debug("task 1 running ...");
            Thread.sleep(1000);
            log.debug("task 1 finish ...");
            return "1";
        });
        Future<String> future2 = pool.submit(() -> {
            log.debug("task 2 running ...");
            Thread.sleep(1000);
            log.debug("task 2 finish ...");
            return "2";
        });
        Future<String> future3 = pool.submit(() -> {
            log.debug("task 3 running ...");
            Thread.sleep(1000);
            log.debug("task 3 finish ...");
            return "3";
        });


//        pool.shutdown();
//        pool.awaitTermination(1000, TimeUnit.MILLISECONDS);
        log.debug("other ...");
        Thread.sleep(1100);
        List<Runnable> runnables = pool.shutdownNow();
        log.debug("other {}...", runnables.size());
    }
}
