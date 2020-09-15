package thread.heima.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

@Slf4j
public class TestInterrupt {
    public static void main(String[] args) throws InterruptedException {
        test2();
    }
    /**
     * 打断正在睡眠的线程
     * @throws InterruptedException
     */
    @Test
    public void test1() throws InterruptedException {
        Thread t1 = new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");
        t1.start();
        Thread.sleep(500);
        t1.interrupt();
        log.debug(" 打断状态: {}", t1.isInterrupted());
    }

    /**
     * 打断正常运行的线程
     * @throws InterruptedException
     */
    public static void test2() throws InterruptedException {
        Thread t2 = new Thread(()->{
            while(true) {
                Thread current = Thread.currentThread();
                boolean interrupted = current.isInterrupted();
//                if(interrupted) {
//                    log.debug(" 打断状态: {}", interrupted);
//                    break;
//                }
            }
        }, "t2");
        t2.start();
        Thread.sleep(500);
        t2.interrupt();
        log.debug(" 打断状态: {}", t2.isInterrupted());
    }

    /**
     * 测试两阶段终止
     * @throws InterruptedException
     */
    @Test
    public void test3() throws InterruptedException {
        TPTInterrupt tpti = new TPTInterrupt();
        tpti.start();
        Thread.sleep(1000);
        log.debug("Stop");
        tpti.stop();

    }

    @Test
    public void test4() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("park...");
            LockSupport.park();
            log.debug("unpark...");
             log.debug("打断状态：{}", Thread.currentThread().isInterrupted());
            log.debug("打断状态：{}", Thread.interrupted());
            LockSupport.park();
            log.debug("unpark...");
        }, "t1");

        t1.start();
        Thread.sleep(1000);
        t1.interrupt();
        t1.join();
    }
}

@Slf4j
class TPTInterrupt{
    private Thread monitor;

    public void start(){
        monitor = new Thread(() -> {
            while(true){
                Thread current = Thread.currentThread();
                if(current.isInterrupted()){
                    log.debug("料理后事");
                    break;
                }
                else{
                    try {
                        Thread.sleep(100);
                        log.debug("保存监控日志");
                    } catch (InterruptedException e) {
                        current.interrupt();
                        e.printStackTrace();
                    }
                }
            }
        }, "监控线程");
        monitor.start();
    }

    public void stop(){
        monitor.stop();
    }
}
