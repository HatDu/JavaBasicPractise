package thread.heima.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

@Slf4j
public class TestSleep {
    @Test
    public void test_01() throws Exception{
        Thread thread = new Thread(() -> {
            try {
                log.debug("开始睡眠");
                TimeUnit.MILLISECONDS.sleep(2000);
            } catch (InterruptedException e) {
                log.debug("睡眠被打断");
                e.printStackTrace();
            }
        }, "t1");

        thread.start();

        TimeUnit.MILLISECONDS.sleep(1000);

        thread.interrupt();
    }
}
