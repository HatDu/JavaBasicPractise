package thread.heima.sync;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestWaitNotify {
    final static Object lock = new Object();
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lock){
                log.debug("执行...");
                try{
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("执行其他代码...");
            }
        }, "t1");

        t1.start();

        Thread t2 = new Thread(() -> {
            synchronized (lock){
                log.debug("执行...");
                lock.notify();
            }
        }, "t2");

        t2.start();
    }
}
