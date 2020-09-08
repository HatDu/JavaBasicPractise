package thread.heima.juc;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TestLongAdder {

    /**
     * ²âÊÔÀÛ¼ÓÆ÷
     */
    @Test
    public void test01(){
        demo(
                () -> new LongAdder(),
                (adder) -> adder.increment()
        );

        demo(
                () -> new AtomicLong(),
                (adder) -> adder.getAndIncrement()
        );
    }

    private static <T> void demo(Supplier<T> addSupplier, Consumer<T> action){
        T adder = addSupplier.get();
        List<Thread> ts = new ArrayList<>();

        for(int i = 0; i < 4; ++i){
            ts.add(new Thread(() -> {
                for(int j = 0; j < 500000; ++j){
                    action.accept(adder);
                }
            }));
        }

        long start = System.nanoTime();
        ts.forEach(t -> t.start());
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.nanoTime();
        System.out.println(adder + " cost: " + (end - start) / 1000_000);
    }
}
