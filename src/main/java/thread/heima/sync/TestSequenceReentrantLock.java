package thread.heima.sync;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TestSequenceReentrantLock {
    public static void main(String[] args) {
        AwaitSignal as = new AwaitSignal(5);
        Condition c1 = as.newCondition();
        Condition c2 = as.newCondition();
        Condition c3 = as.newCondition();

        new Thread(() -> {
            as.print(c1, c2, "a");
        }, "t1").start();

        new Thread(() -> {
            as.print(c2, c3, "b");
        }, "t2").start();

        new Thread(() -> {
            as.print(c3, c1, "c");
        }, "t3").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        as.lock();
        try{
            c1.signal();
        }finally {
            as.unlock();
        }
    }
}

class AwaitSignal extends ReentrantLock{
    private int loopNumber;

    public AwaitSignal(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    public void print(Condition current, Condition next, String msg){
        for(int i = 0; i < loopNumber; ++i){
            lock();
            try{
                current.await();
                System.out.print(msg);
                next.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                unlock();
            }
        }
    }
}
