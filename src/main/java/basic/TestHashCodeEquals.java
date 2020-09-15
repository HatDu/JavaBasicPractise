package basic;

import java.util.HashSet;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.LockSupport;

class Student{
    String name;
    static int id = 0;
    final int sid;
    public Student(String name) {
        this.name = name;
        sid = ++id;
    }

//    @Override
//    public int hashCode() {
//        return 1 + id;
//    }

    @Override
    public boolean equals(Object obj) {
        Student other = (Student)obj;
        return this.name.equals(other.name);
    }
}

public class TestHashCodeEquals {
    public static void main(String[] args) {
        Student a = new Student("张三");
        Student b = new Student("张三");

        System.out.println(a.equals(b));
        System.out.println(a.sid == b.sid);
        System.out.println(a.hashCode() == b.hashCode());

        HashSet<Student> set = new HashSet<>();
        set.add(a);
        set.add(b);
        System.out.println(set.size());
    }
}
