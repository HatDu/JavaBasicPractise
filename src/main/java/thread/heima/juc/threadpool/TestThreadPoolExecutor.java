package thread.heima.juc.threadpool;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class TestThreadPoolExecutor {
    public static void main(String[] args) {

        testSinglePool();
        //testFixedThreadPool();
    }

    public static void testSinglePool(){
        ExecutorService pool =  Executors.newSingleThreadExecutor();
        pool.execute(() -> {
            int j = 1 / 0;
            log.debug("1");

        });
        pool.execute(() -> { log.debug("2");});
        pool.execute(() -> { log.debug("3");});
        pool.shutdown();
    }

    public static void testFixedThreadPool(){
        ExecutorService pool = Executors.newFixedThreadPool(2, new ThreadFactory() {
            private AtomicInteger t = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "pool_t" + t.getAndIncrement());
            }
        });

        pool.execute(() -> { log.debug("1");});
        pool.execute(() -> { log.debug("2");});
        pool.execute(() -> { log.debug("3");});
        pool.shutdown();
    }
}
