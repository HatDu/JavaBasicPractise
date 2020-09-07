package thread.heima.sync;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestLiveLock {
    static final Object lock = new Object();
    static volatile int count = 10;
    public static void main(String[] args) {
        new Thread(() -> {
            // 期望减到 0 退出循环
            while (count > 0) {
                sleep(200);
                count--;
                log.debug("count: {}", count);
            }
        }, "t1").start();
        new Thread(() -> {
            // 期望超过 20 退出循环
            while (count < 20) {
                sleep(200);
                count++;
                log.debug("count: {}", count);
            }
        }, "t2").start();
    }

    private static void sleep(int v) {
        try {
            Thread.sleep(v);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
