package thread.heima.juc;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class TestAtomicClass {

    /**
     * �򵥵�����
     */
    @Test
    public void test01(){
        AtomicInteger atomicInteger = new AtomicInteger(10);
        //����1
        System.out.println(atomicInteger.getAndIncrement());
        System.out.println(atomicInteger.incrementAndGet());

        // �Լ�1
        System.out.println(atomicInteger.getAndDecrement());
        System.out.println(atomicInteger.decrementAndGet());

        // ����n
        System.out.println(atomicInteger.addAndGet(100));
    }

    /**
     * ��������
     */
    @Test
    public void test02(){
        AtomicInteger atomicInteger = new AtomicInteger(10);
        System.out.println(atomicInteger.updateAndGet(x -> x * 10));
        System.out.println(atomicInteger.updateAndGet(x -> x*x + 1));
    }
}
