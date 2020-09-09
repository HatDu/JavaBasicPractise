package thread.heima.juc.atomic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class TestArray {

    public static void main(String[] args) {

    }
    @Test
    public void test01(){
        demo(
                () -> new int[10],
                (array) -> array.length,
                (array, index) -> ++array[index],
                array -> System.out.println(Arrays.toString(array))
        );

        demo(
                () -> new AtomicIntegerArray(10),
                (array) -> array.length(),
                (array, index) -> array.getAndIncrement(index),
                array -> System.out.println(array)
        );
    }
    /**
     ����1���ṩ���顢�������̲߳���ȫ������̰߳�ȫ����
     ����2����ȡ���鳤�ȵķ���
     ����3�������������ش� array, index
     ����4����ӡ����ķ���
     */
    // supplier �ṩ�� �������� ()->���
    // function ���� һ������һ����� (����)->��� , BiFunction (����1,����2)->���
    // consumer ������ һ������û��� (����)->void, BiConsumer (����1,����2)->
    private static <T> void demo(
            Supplier<T> arraySupplier,
            Function<T, Integer> lengthFun,
            BiConsumer<T, Integer> putConsumer,
            Consumer<T> printConsumer ) {
        List<Thread> ts = new ArrayList<>();
        T array = arraySupplier.get();
        int length = lengthFun.apply(array);
        for (int i = 0; i < length; i++) {
            // ÿ���̶߳������� 10000 �β���
            ts.add(new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    putConsumer.accept(array, j%length);
                }
            }));
        }
        ts.forEach(t -> t.start()); // ���������߳�
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }); // �������߳̽���
        printConsumer.accept(array);
    }
}