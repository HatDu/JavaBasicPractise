package thread.heima.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static thread.heima.basic.Sleep.sleepMS;

@Slf4j
public class TestMultiThread {
    public static void main(String[] args) {
        new Thread(() ->{
            while(true){
                log.debug(Thread.currentThread() + "running");
            }
        }, "t1").start();

        new Thread(() ->{
            while(true){
                log.debug(Thread.currentThread() + "running");
            }
        }, "t2").start();
    }

    static int r1 = 0;
    static int r2 = 0;

    @Test
    public void test2() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            sleepMS(1);
            r1 = 10;
        });
        Thread t2 = new Thread(() -> {
            sleepMS(2);
            r2 = 20;
        });
        long start = System.currentTimeMillis();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        long end = System.currentTimeMillis();
        log.debug("r1: {} r2: {} cost: {}", r1, r2, end - start);
    }
}
