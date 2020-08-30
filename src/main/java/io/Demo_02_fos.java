package io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Demo_02_fos {
    public static void main(String[] args) throws Exception {
        test_01();
    }

    public static void test_01() throws Exception {
        FileOutputStream fos = new FileOutputStream("src/main/resources/io/data_demo_02.txt");

        fos.write(92);
        fos.write(91);
        fos.write(99);
        fos.write("\nHello Java IO".getBytes());
        fos.close();
    }
}
