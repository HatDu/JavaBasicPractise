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
     * ���Կ�����
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
     * ���Կɴ����
     */
    @Test
    public void test02() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                log.debug("���Ի����");
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("û�л����������");
            }
            try{
                log.debug("��ȡ����");
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
     * ���Գ�ʱ
     */
    @Test
    public void test03() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("���Ի����");
//            if (!lock.tryLock()){
//                log.debug("��ȡ������");
//                return;
//            }
            try {
                if (!lock.tryLock(1000, TimeUnit.MILLISECONDS)){
                    log.debug("��ȡ������");
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try{
                log.debug("��ȡ����");
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
     * ��������
     */
    @Test
    public void test04(){
        Thread t1 = new Thread(() -> {
            room.lock();
            try {
                log.debug("����û��[{}]", hasCigarette);
                while (!hasCigarette) {
                    log.debug("û���̣���Ъ��");
                    try {
                        waitCigarette.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                hasCigarette = false;
                log.debug("��ʼ�ɻ�");
            } finally {
                room.unlock();
            }

        }, "����");

        Thread t2 = new Thread(() -> {
            room.lock();
            try {
                log.debug("������û��[{}]", hasTakeout);
                while (!hasTakeout) {
                    log.debug("û����������Ъ��");
                    try {
                        waitTakeout.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                hasTakeout = false;
                log.debug("��ʼ�ɻ�");
            } finally {
                room.unlock();
            }

        }, "С��");

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
        },"����").start();

        new Thread(() -> {
            room.lock();
            try {
                hasTakeout = true;
                waitTakeout.signal();
            }finally {
                room.unlock();
            }
        },"������").start();
    }
}
