package thread.heima.juc.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
public class TestTask {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test2();
    }

    public static void test1() throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        log.debug("线程提交");
        Future<String> future = pool.submit(() -> {
            Thread.sleep(1000);
            return "ok";
        });

        log.debug(future.get());
        pool.shutdown();
    }

    public static void test2() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        List<Future<String>> futures = pool.invokeAll(Arrays.asList(
                () ->{
                    log.debug("begin");
                    Thread.sleep(1000);
                    return "1";
                },
                () ->{
                    log.debug("begin");
                    Thread.sleep(1000);
                    return "2";
                },
                () ->{
                    log.debug("begin");
                    Thread.sleep(1000);
                    return "3";
                }
        ));

        futures.forEach(f -> {
            try {
                log.debug(f.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        pool.shutdown();
    }
}
