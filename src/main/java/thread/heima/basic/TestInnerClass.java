package thread.heima.basic;

import com.sun.org.apache.bcel.internal.classfile.InnerClass;

import java.sql.PseudoColumnUsage;

class Person{
    String name;
    Integer age;

    Integer length;

    class Arm{
        Integer length;

        public Arm(Integer length) {
            this.length = length;
        }

        @Override
        public String toString() {
            return name + " 身高：" + Person.this.length + ", 手长：" + this.length ;
        }
    }

    public Person(String name, Integer age, Integer length) {
        this.name = name;
        this.age = age;
        this.length = length;
    }

    protected static class Shoe{
        Integer length;
    }
}



public class TestInnerClass {
    public static void main(String[] args) {
        Person p = new Person("张三", 23, 175);
        Person.Arm arm = p.new Arm(10);
        System.out.println(arm);
        Person.Shoe shoe = new Person.Shoe();
    }
}
