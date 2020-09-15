package thread.heima.safecollection;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
@Slf4j
public class TestWordCount {
    public static void main(String[] args) {
//        ConcurrentHashMap
        log.debug("start ...");
        demo(
                // ���� map ����
                () -> new ConcurrentHashMap<String, LongAdder>(),

                (map, words) -> {
                    for (String word : words) {
//                        Integer counter = map.get(word);
//                        int newVal = counter == null ? 1 : counter + 1;
//                        map.put(word, newVal);

                        // ���������������һ��value
                        LongAdder value = map.computeIfAbsent(word, (key) -> new LongAdder());
                        // ��֤�ۼ�����ԭ�ӵ�
                        value.increment();
                    }
                }
        );
        log.debug("end ...");
//        demo(
//                // ���� map ����
//                // ���� ConcurrentHashMap �Բ��ԣ�
//                () -> new ConcurrentHashMap<String, LongAdder>(8,0.75f,8),
//
//                (map, words) -> {
//                    for (String word : words) {
//
//                        // ���ȱ��һ�� key�����������һ�� value , Ȼ��  key value ���� map
//                        //                  a      0
//                        LongAdder value = map.computeIfAbsent(word, (key) -> new LongAdder());
//                        // ִ���ۼ�
//                        value.increment(); // 2
//
//                        /*// ��� key ��û��
//                        Integer counter = map.get(word);
//                        int newValue = counter == null ? 1 : counter + 1;
//                        // û�� �� put
//                        map.put(word, newValue);*/
//                    }
//                }
//        );
    }


    private static void demo2() {

        Map<String, Integer> collect = IntStream.range(1, 27).parallel()
                .mapToObj(idx -> readFromFile(idx))
                .flatMap(list -> list.stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(w -> 1)));
        System.out.println(collect);
    }

    private static <V> void demo(Supplier<Map<String, V>> supplier, BiConsumer<Map<String, V>, List<String>> consumer) {
        Map<String, V> counterMap = supplier.get();
        // key value
        // a   200
        // b   200
        List<Thread> ts = new ArrayList<>();
        for (int i = 1; i <= 26; i++) {
            int idx = i;
            Thread thread = new Thread(() -> {
                List<String> words = readFromFile(idx);
                consumer.accept(counterMap, words);
            });
            ts.add(thread);
        }

        ts.forEach(t -> t.start());
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(counterMap);
    }

    public static List<String> readFromFile(int i) {
        ArrayList<String> words = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("tmp/" + i + ".txt")))) {
            while (true) {
                String word = in.readLine();
                if (word == null) {
                    break;
                }
                words.add(word);
            }
            return words;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
