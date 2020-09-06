package thread.interview;

import org.omg.PortableInterceptor.ObjectReferenceFactory;
import org.openjdk.jol.info.ClassLayout;

public class TestMarkWord {
    public static void main(String[] args) {
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        synchronized (o){
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }
}
