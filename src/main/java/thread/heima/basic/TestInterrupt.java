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
     * �������˯�ߵ��߳�
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
        log.debug(" ���״̬: {}", t1.isInterrupted());
    }

    /**
     * ����������е��߳�
     * @throws InterruptedException
     */
    public static void test2() throws InterruptedException {
        Thread t2 = new Thread(()->{
            while(true) {
                Thread current = Thread.currentThread();
                boolean interrupted = current.isInterrupted();
//                if(interrupted) {
//                    log.debug(" ���״̬: {}", interrupted);
//                    break;
//                }
            }
        }, "t2");
        t2.start();
        Thread.sleep(500);
        t2.interrupt();
        log.debug(" ���״̬: {}", t2.isInterrupted());
    }

    /**
     * �������׶���ֹ
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
             log.debug("���״̬��{}", Thread.currentThread().isInterrupted());
            log.debug("���״̬��{}", Thread.interrupted());
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
                    log.debug("�������");
                    break;
                }
                else{
                    try {
                        Thread.sleep(100);
                        log.debug("��������־");
                    } catch (InterruptedException e) {
                        current.interrupt();
                        e.printStackTrace();
                    }
                }
            }
        }, "����߳�");
        monitor.start();
    }

    public void stop(){
        monitor.stop();
    }
}
