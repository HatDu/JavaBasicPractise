package thread.heima.juc.aqs;

import com.sun.org.apache.xerces.internal.dom.PSVIAttrNSImpl;
import lombok.extern.slf4j.Slf4j;
import org.omg.CosNaming._NamingContextExtStub;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

public class TestStampedLock {
    public static void main(String[] args) throws InterruptedException {
        Data d = new Data();

        // test read-read
        new Thread(() -> {
            d.read(1000);
        }, "t1").start();
        Thread.sleep(500);
        new Thread(() -> {
            d.write(1000);
        }, "t2").start();
    }
}

@Slf4j
class Data{
    Object data;
    private final StampedLock lock = new StampedLock();
    public Object read(int readTime){
        long stamp = lock.tryOptimisticRead();
        log.debug("optimistic read locking {}", stamp);
        try {
            Thread.sleep(readTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(lock.validate(stamp)){
            log.debug("read finished {}", stamp);
            return data;
        }
        // ËøÉý¼¶ - ¶ÁËø
        log.debug("upgrade to read lock {}", stamp);
        try{
            stamp = lock.readLock();
            log.debug("read lock {}", stamp);
            Thread.sleep(readTime);
            log.debug("read finished {}", stamp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            log.debug("read unlock {}", stamp);
            lock.unlockRead(stamp);
        }
        return data;
    }

    public void write(Object _data){
        long stamp = lock.writeLock();

        try {
            log.debug("write lock {}", stamp);

            try {
                Thread.sleep(1000);
                this.data = _data;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("write unlock {}", stamp);
        }finally {
            lock.unlockWrite(stamp);
        }
    }
}