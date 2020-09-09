package thread.heima.juc.atomic;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

@Slf4j
public class TestAtomicABA {

    public static void main(String[] args) {

    }
    static AtomicReference<String> ref = new AtomicReference<>("a");
    public static void test01(){
        log.debug("main start ...");

        String prev = ref.get();
        other();

        log.debug("change a -> c {}", ref.compareAndSet(prev,"c"));
    }

    public static void other(){
        ref.compareAndSet("a", "b");

        ref.compareAndSet("b", "a");
    }

    static AtomicStampedReference<String> stampedRef = new AtomicStampedReference<>("a", 0);

    @Test
    public void test02(){
        String prev = stampedRef.getReference();
        int stamp = stampedRef.getStamp();
        other02("b");
        other02("a");
        log.debug("main a -> c {}", stampedRef.compareAndSet(prev, "c", stamp, stamp + 1));
    }

    public void other02(String newStr){
        String prev = stampedRef.getReference();
        int stamp = stampedRef.getStamp();
        stampedRef.compareAndSet(prev, newStr, stamp, stamp + 1);
    }

    static AtomicMarkableReference<String> markableRef = new AtomicMarkableReference<>("a", false);

    @Test
    public void test03(){
        String prev = markableRef.getReference();
        boolean expFlag = markableRef.isMarked();
        other03("b");
        other03("a");
        log.debug("main a -> c {}", markableRef.compareAndSet(prev, "b", expFlag, !expFlag));
    }

    public void other03(String changeVal){
        String prev = markableRef.getReference();
        boolean expFlag = markableRef.isMarked();
        markableRef.compareAndSet(prev, "b", expFlag, !expFlag);
    }
}
