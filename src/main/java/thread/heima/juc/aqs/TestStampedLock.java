package thread.heima.juc.aqs;

import lombok.extern.slf4j.Slf4j;
import org.omg.CosNaming._NamingContextExtStub;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

public class TestStampedLock {
}

@Slf4j
class Data{
    Object data;
    private final StampedLock lock = new StampedLock();
    public Object read(){
        long stamp = lock.tryOptimisticRead();
        if(lock.validate(stamp)){
            log.debug("read finish {}", stamp);
            return data;
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