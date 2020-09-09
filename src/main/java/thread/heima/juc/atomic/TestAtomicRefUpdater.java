package thread.heima.juc.atomic;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class TestAtomicRefUpdater {
    public static void main(String[] args) {
        Student stu = new Student();

        AtomicReferenceFieldUpdater updater = AtomicReferenceFieldUpdater.newUpdater(Student.class, String.class, "name");
        //stu.name = "李四";
        System.out.println(updater.compareAndSet(stu, null, "张三"));;
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
