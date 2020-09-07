package thread.interview;

import java.util.concurrent.locks.LockSupport;

public class TestSequenceParkUnpark {
    static  Thread t1 = null, t2 = null, t3 = null;
    public static void main(String[] args) {
        SyncParkUnpark spu = new SyncParkUnpark(5);

        t1 = new Thread(() -> {
            spu.print("a", t2);
        }, "t1");

        t2 = new Thread(() -> {
            spu.print("b", t3);
        }, "t1");

        t3 = new Thread(() -> {
            spu.print("c", t1);
        }, "t1");

        t1.start();
        t2.start();
        t3.start();

        LockSupport.unpark(t1);
    }
}


class SyncParkUnpark{
    private int loopNumber;

    public SyncParkUnpark(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    public void print(String msg, Thread next){
        for(int i = 0; i < loopNumber; ++i){
            LockSupport.park();
            System.out.print(msg);
            LockSupport.unpark(next);
        }
    }
}