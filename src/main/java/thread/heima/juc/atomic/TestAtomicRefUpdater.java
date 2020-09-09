package thread.heima.juc.atomic;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class TestAtomicRefUpdater {
    public static void main(String[] args) {
        Student stu = new Student();

        AtomicReferenceFieldUpdater updater = AtomicReferenceFieldUpdater.newUpdater(Student.class, String.class, "name");
        //stu.name = "����";
        System.out.println(updater.compareAndSet(stu, null, "����"));;
    }
}

class Student{
    volatile String name;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
