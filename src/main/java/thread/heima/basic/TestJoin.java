package thread.heima.basic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class TestJoin {
    static int r = 0;
    public static void main(String[] args) throws InterruptedException {
        test1();
    }
    private static void test1() throws InterruptedException {
        log.debug("��ʼ");
        Thread t1 = new Thread(() -> {
            log.debug("��ʼ");
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("����");
            r = 10;
        });
        t1.start();
        t1.join();
        log.debug("���Ϊ:{}", r);
        log.debug("����");
    }
}
