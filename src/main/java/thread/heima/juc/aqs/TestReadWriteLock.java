package thread.heima.juc.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestReadWriteLock {
    public static void main(String[] args) {
        DataContainer dataContainer = new DataContainer();

        new Thread(() -> {
            dataContainer.read();

        }, "t1").start();

        new Thread(() -> {
            dataContainer.write(new Object());
        }, "t2").start();
    }
}

@Slf4j
class DataContainer{
    Object data;
    ReentrantReadWriteLock wr = new ReentrantReadWriteLock();
    ReentrantReadWriteLock.ReadLock r = wr.readLock();
    ReentrantReadWriteLock.WriteLock w = wr.writeLock();
    public Object read(){
        r.lock();

        try {
            log.debug("读数据");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("读取结束");
            return data;
        }finally {
            r.unlock();
        }
    }

    public void write(Object _data){
        w.lock();

        try {
            log.debug("写数据");
            data = _data;
        }finally {
            w.unlock();
        }
    }
}