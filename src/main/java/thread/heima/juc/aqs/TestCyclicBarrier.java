package thread.heima.juc.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class TestCyclicBarrier {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);

        CyclicBarrier cb = new CyclicBarrier(2, () -> {
            log.debug("task1 task2 finished");
            service.shutdown();
        });

        for(int i = 0; i < 3; ++i){
            service.submit(() ->{
                log.debug("task begin ...");
                try {
                    Thread.sleep(1000);
                    try {

                        log.debug("task end ...");
                        cb.await();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            service.submit(() ->{
                log.debug("task begin ...");
                try {
                    Thread.sleep(1000);
                    try {

                        log.debug("task end ...");
                        cb.await();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
