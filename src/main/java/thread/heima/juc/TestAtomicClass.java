package thread.heima.juc;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class TestAtomicClass {

    /**
     * 简单的增减
     */
    @Test
    public void test01(){
        AtomicInteger atomicInteger = new AtomicInteger(10);
        //自增1
        System.out.println(atomicInteger.getAndIncrement());
        System.out.println(atomicInteger.incrementAndGet());

        // 自减1
        System.out.println(atomicInteger.getAndDecrement());
        System.out.println(atomicInteger.decrementAndGet());

        // 增加n
        System.out.println(atomicInteger.addAndGet(100));
    }

    /**
     * 复杂运算
     */
    @Test
    public void test02(){
        AtomicInteger atomicInteger = new AtomicInteger(10);
        System.out.println(atomicInteger.updateAndGet(x -> x * 10));
        System.out.println(atomicInteger.updateAndGet(x -> x*x + 1));
    }
}
