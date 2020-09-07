package thread.heima.juc;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class TestEnntrentLock {
    private static ReentrantLock lock = new ReentrantLock();

    /**
     * 测试可重入
     */
    @Test
    public void test01(){
        lock.lock();
        try {
            log.debug("Entering test01");
            test01_1();
        }finally {
            lock.unlock();
        }
    }

    public void test01_1(){
        lock.lock();
        try {
            log.debug("Entering test01_1");
            test01_2();
        }finally {
            lock.unlock();
        }
    }
    public void test01_2(){
        lock.lock();
        try {
            log.debug("Entering test01_2");
        }finally {
            lock.unlock();
        }
    }

    /**
     * 测试可打断锁
     */
    @Test
    public void test02() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                log.debug("尝试获得锁");
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("没有获得锁，返回");
            }
            try{
                log.debug("获取到锁");
            }finally {
                lock.unlock();
            }
        }, "t1");

        lock.lock();
        t1.start();

        Thread.sleep(500);
        t1.interrupt();

        t1.join();
    }

    /**
     * 测试超时
     */
    @Test
    public void test03() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("尝试获得锁");
//            if (!lock.tryLock()){
//                log.debug("获取不到锁");
//                return;
//            }
            try {
                if (!lock.tryLock(1000, TimeUnit.MILLISECONDS)){
                    log.debug("获取不到锁");
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try{
                log.debug("获取到锁");
            }finally {
                lock.unlock();
            }
        }, "t1");

        lock.lock();
        t1.start();

        Thread.sleep(500);
        lock.unlock();
    }

    private static final ReentrantLock room = new ReentrantLock();
    private static Condition waitCigarette = room.newCondition();
    private static Condition waitTakeout = room.newCondition();
    private static boolean hasCigarette = false;
    private static boolean hasTakeout = false;
    /**
     * 条件变量
     */
    @Test
    public void test04(){
        Thread t1 = new Thread(() -> {
            room.lock();
            try {
                log.debug("有烟没？[{}]", hasCigarette);
                while (!hasCigarette) {
                    log.debug("没有烟，先歇会");
                    try {
                        waitCigarette.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                hasCigarette = false;
                log.debug("开始干活");
            } finally {
                room.unlock();
            }

        }, "老王");

        Thread t2 = new Thread(() -> {
            room.lock();
            try {
                log.debug("有外卖没？[{}]", hasTakeout);
                while (!hasTakeout) {
                    log.debug("没有外卖，先歇会");
                    try {
                        waitTakeout.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                hasTakeout = false;
                log.debug("开始干活");
            } finally {
                room.unlock();
            }

        }, "小南");

        t1.start();
        t2.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            room.lock();
            try {
                hasCigarette = true;
                waitCigarette.signal();
            }finally {
                room.unlock();
            }
        },"送烟").start();

        new Thread(() -> {
            room.lock();
            try {
                hasTakeout = true;
                waitTakeout.signal();
            }finally {
                room.unlock();
            }
        },"送外卖").start();
    }
}
