package thread.heima.juc.aqs;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class TestAQS {
    public static void main(String[] args) {
        ReentrantLock loc = new ReentrantLock();
        MyLock lock = new MyLock();
        new Thread(() -> {
            lock.lock();

            try {
                log.debug("locking ...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                log.debug("unlocking ...");
                lock.unlock();
            }
        }, "t1").start();
        new Thread(() -> {
            lock.lock();

            try {
                log.debug("locking ...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                log.debug("unlocking ...");
                lock.unlock();
            }
        }, "t2").start();
    }
}

class  MyLock implements Lock{
    // 独占锁
    class MySync extends AbstractQueuedLongSynchronizer{
        @Override
        protected boolean tryAcquire(long arg) {
            if(compareAndSetState(0, 1)){
                // 成功加锁
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(long arg) {
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        public Condition newCondition(){
            return new ConditionObject();
        }
    }

    private MySync sync = new MySync();


    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
