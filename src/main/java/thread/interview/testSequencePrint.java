package thread.interview;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class S{
    void f(){
        for(int i = 1; i < 10; ++i){
            synchronized (this){
                System.out.print(i + " ");
                this.notify();
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class S1{
    private volatile static Object lock = new Object();
    void f(){
        for(int i = 1; i < 10; ++i){
            synchronized (lock){
                System.out.print(i + " ");
                lock.notify();
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

public class testSequencePrint {
    @Test
    public void testSingleObject(){
        S s = new S();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> s.f());
        executorService.execute(() -> s.f());
    }

    @Test
    public void testDifferentObject(){
        S1 s = new S1();
        S1 ss = new S1();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> s.f());
        executorService.execute(() -> ss.f());
    }

}
