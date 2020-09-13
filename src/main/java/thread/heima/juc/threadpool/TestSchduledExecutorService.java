package thread.heima.juc.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TestSchduledExecutorService {
    public static void main(String[] args) {
        testRound();
    }
    public static void testRound(){
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);

        // 如果执行时间超过循环周期，后面的线程会被推迟，不会重叠
        pool.scheduleAtFixedRate(() -> {
            log.debug("running ...");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, 1, TimeUnit.SECONDS);

        // 计时是从上一个线程结束时开始的
//        pool.scheduleWithFixedDelay(() -> {
//            log.debug("running ...");
//            try {
//                // 如果执行时间超过循环周期，后面的线程会被推迟，不会重叠
//                Thread.sleep(1500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, 1, 1, TimeUnit.SECONDS);

    }
    public static void testDelay(){
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        log.debug("开始运行");
        pool.schedule(()->{
            log.debug("task1");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, TimeUnit.SECONDS);

        pool.schedule(()->{
            log.debug("task2");
        }, 1, TimeUnit.SECONDS);

        pool.shutdown();
    }
}
