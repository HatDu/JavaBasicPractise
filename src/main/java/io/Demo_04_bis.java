package io;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.nio.Buffer;

public class Demo_04_bis {
    public static void main(String[] args) throws Exception{
//        test_01();
        test_02();
    }

    public static void test_01() throws Exception{
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream("src/main/resources/io/data_demo_01.txt"));

        int data = 0;
        while((data = bis.read()) != -1){
            System.out.println((char)data);
        }

        bis.close();
    }

    public static void test_02() throws Exception{
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream("src/main/resources/io/data_demo_01.txt"));

        int count = 0;
        byte[] buf = new byte[3];
        while((count = bis.read(buf)) != -1){
            System.out.println(new String(buf, 0, count));
        }

        bis.close();
    }
}
