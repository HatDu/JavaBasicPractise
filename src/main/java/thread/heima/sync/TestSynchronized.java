package thread.heima.sync;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class TestSynchronized {
    private int count  = 0;


    @Test
    public void test1() throws InterruptedException {
        count = 0;
        Thread t1 = new Thread(() -> {
            for(int i = 0; i < 5000; ++i){
                count++;
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for(int i = 0; i < 5000; ++i){
                count--;
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("count = {}", count);
    }


    private static Object lock = new Object();
    @Test
    public void test2() throws InterruptedException {
        count = 0;
        Thread t1 = new Thread(() -> {
            for(int i = 0; i < 5000; ++i){
                synchronized (lock){
                    count++;
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for(int i = 0; i < 5000; ++i){
                synchronized (lock){
                    count--;
                }
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("count = {}", count);
    }

    class Room{
        private int count = 0;

        public void increment(){
            synchronized (this){
                ++count;
            }
        }
        public void descrement(){
            synchronized (this){
                --count;
            }
        }
        public int get(){
            synchronized (this){
                return count;
            }
        }
    }

    @Test
    public void test3() throws InterruptedException {
        Room room = new Room();

        Thread t1 = new Thread(() -> {
            for(int i = 0; i < 20000; ++i){
                room.increment();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for(int i = 0; i < 20000; ++i){
                room.descrement();
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("result = {}", room.get());
    }
}
