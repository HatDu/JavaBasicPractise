package io;

import javax.print.DocFlavor;
import java.io.*;

/**
 * 序列化与反序列化
 */
class Person implements Serializable {
    String name;
    int age;
    String gender;

    public Person(String _name, int _age, String _gender){
        name = _name;
        age = _age;
        gender = _gender;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}

public class Demo_06_serilizable {
    public static void main(String[] args) throws Exception {

        // 序列化
        String objPath = "src/main/resources/io/data_demo_06_张三.bin";
        FileOutputStream fos = new FileOutputStream(objPath);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        Person zhangsan = new Person("张三", 18, "男");

        oos.writeObject(zhangsan);

        oos.close();

        // 反序列化
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(objPath));
        Person lisi = (Person) ois.readObject();
        System.out.println(lisi);
    }
}
