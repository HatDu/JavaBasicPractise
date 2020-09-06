package thread.heima.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j
public class TestParkUnpark {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("start...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("park...");
            LockSupport.park();
            log.debug("resume...");
        },"t1");
        t1.start();
        log.debug("unpark...");
        LockSupport.unpark(t1);
    }
}
