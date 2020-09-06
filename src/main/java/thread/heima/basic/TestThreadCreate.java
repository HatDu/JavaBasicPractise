package thread.heima.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

@Slf4j(topic = "c.Test1")
public class TestThreadCreate {
    public void sleep(int ms){
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_01(){
        Thread t = new Thread(){
            @Override
            public void run() {
                log.debug("running");
            }
        };
        t.setName("t1");
        t.start();
        log.debug("running");
        sleep(10);
    }

    @Test
    public void test_02(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                log.debug("running");
            };
        };
        Thread t1 = new Thread(runnable);
        t1.setName("t1");
        t1.start();
        log.debug("running");
        sleep(10);
    }


    @Test
    public void test_03(){
        new Thread(() -> {
            log.debug("running");
        }, "t1").start();
        log.debug("running");
        sleep(10);
    }

    @Test
    public void test_04() throws Exception{
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.debug("running");
                Thread.sleep(1000);
                return "执行结束";
            }
        });
        Thread t = new Thread(futureTask,"t");
        t.start();

        // 主线程会陷入阻塞
        String rst = futureTask.get();
        log.debug("执行结果：{}", rst);
        sleep(10);
    }
}
