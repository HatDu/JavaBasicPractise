package thread.interview;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class TestThreadLocal {
    public static void main(String[] args) {
        ThreadLocal<String> local = new ThreadLocal<>();

        Random random = new Random(10);
        IntStream.range(0, 5).forEach(a -> new Thread(() ->{
            local.set(a + " " + random.nextInt(10));
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("�̺߳�Localֵ�ֱ�Ϊ��" + local.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start());
        System.out.println("���̵߳�ThreadLocalֵΪ��" + local.get());
    }
}
