package thread.heima.juc.atomic;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class TestUnsafe {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);

        Unsafe unsafe = (Unsafe) theUnsafe.get(null);

        System.out.println(unsafe);


        //1. 获取域的偏移地址
        long idOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("id"));
        long nameOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("name"));


        Teacher t = new Teacher();
        // 2. 执行cas操作
        unsafe.compareAndSwapInt(t, idOffset, 0, 1);
        unsafe.compareAndSwapObject(t, nameOffset, null, "张三");
        // 3. 验证
        System.out.println(t);
    }
}


class Teacher{
    volatile int id;
    volatile String name;

    public Teacher() {
        
    }

    public Teacher(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}