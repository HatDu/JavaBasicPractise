package thread.heima.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;

@Slf4j
public class TestSafeClass {
    public static void main(String[] args) throws InterruptedException {
        Hashtable<String, Integer> table = new Hashtable<>();

        table.put("One", 1);

        Thread t1 = new Thread(() -> {
            if(table.containsKey("One")){
                table.put("One", 0);
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            if(table.containsKey("One")){
                table.put("One", 2);
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        log.debug(String.valueOf(table.get("One")));
    }
}
