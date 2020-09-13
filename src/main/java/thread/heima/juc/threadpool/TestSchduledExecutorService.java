package thread.heima.juc.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TestSchduledExecutorService {
    public static void main(String[] args) {
        testRound();
    }
    public static void testRound(){
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);

        // ���ִ��ʱ�䳬��ѭ�����ڣ�������̻߳ᱻ�Ƴ٣������ص�
        pool.scheduleAtFixedRate(() -> {
            log.debug("running ...");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, 1, TimeUnit.SECONDS);

        // ��ʱ�Ǵ���һ���߳̽���ʱ��ʼ��
//        pool.scheduleWithFixedDelay(() -> {
//            log.debug("running ...");
//            try {
//                // ���ִ��ʱ�䳬��ѭ�����ڣ�������̻߳ᱻ�Ƴ٣������ص�
//                Thread.sleep(1500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, 1, 1, TimeUnit.SECONDS);

    }
    public static void testDelay(){
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        log.debug("��ʼ����");
        pool.schedule(()->{
            log.debug("task1");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, TimeUnit.SECONDS);

        pool.schedule(()->{
            log.debug("task2");
        }, 1, TimeUnit.SECONDS);

        pool.shutdown();
    }
}
