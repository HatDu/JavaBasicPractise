package thread.heima.juc.aqs;

import lombok.extern.slf4j.Slf4j;
import sun.nio.ch.ThreadPool;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.CheckedOutputStream;

@Slf4j
public class TestCountDownLatch {
    public static void main(String[] args) throws InterruptedException {
        // test3();
    }

    /**
     * 王者荣耀
     * @throws InterruptedException
     */
    public static void test3() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch cd = new CountDownLatch(10);
        Random r = new Random();
        String[] all = new String[10];
        for(int uid = 0; uid < 10; ++uid){
            int tmpid = uid;
            service.submit(() -> {
                for(int i = 0; i <= 100; ++i){
                    try {
                        Thread.sleep(r.nextInt(100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    all[tmpid] = i + "%";
                    System.out.print("\r" + Arrays.toString(all));
                }
                cd.countDown();
            });
        }

        cd.await();

        System.out.println("\n加载完毕!");
        service.shutdown();

    }

    public static void test2() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);
        ExecutorService service = Executors.newFixedThreadPool(4);

        service.submit(() -> {
            log.debug("begin ...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            latch.countDown();
            log.debug("end ...{}", latch.getCount());
        });

        service.submit(() -> {
            log.debug("begin ...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
            log.debug("end ...{}", latch.getCount());
        });

        service.submit(() -> {
            log.debug("begin ...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            latch.countDown();
            log.debug("end ...{}", latch.getCount());
        });

        latch.await();
        service.shutdown();
    }

    public static void test1() throws InterruptedException {
        CountDownLatch cd = new CountDownLatch(3);
        new Thread(() -> {
            log.debug("begin ...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.debug("end ...");
            cd.countDown();
        }, "t1").start();

        new Thread(() -> {
            log.debug("begin ...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.debug("end ...");
            cd.countDown();
        }, "t2").start();

        new Thread(() -> {
            log.debug("begin ...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.debug("end ...");
            cd.countDown();
        }, "t3").start();
        cd.await();
        log.debug("执行结束");
    }
}
