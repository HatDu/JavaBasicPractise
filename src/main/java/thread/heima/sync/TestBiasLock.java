package thread.heima.sync;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

@Slf4j
public class TestBiasLock {
    public static void main(String[] args) {
        Dog dog = new Dog();
        ClassLayout classLayout = ClassLayout.parseInstance(dog);
        new Thread(() -> {
            log.debug("Synchronized ǰ");
            log.debug(classLayout.toPrintable());

            synchronized (dog){
                log.debug("Synchronized ��");
                log.debug(classLayout.toPrintable());
            }

            log.debug("Synchronized ��");
            log.debug(classLayout.toPrintable());
        }, "t1").start();
    }
}

class Dog{

}
