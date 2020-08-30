package io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class Demo_01_fis {
    public static void main(String[] args) throws Exception {
        // test_01();
        test_02();
    }

    /**
     * 读取单个字节
     */
    public static void test_01() throws Exception{
        // 1 创建文件输入流
        FileInputStream fis = new FileInputStream("src/main/resources/io/data_demo_01.txt");

        // 2 读取文件
        int data = 0;
        while((data = fis.read()) != -1){
            System.out.println((char) data);
        }
        // 3 关闭
        fis.close();
    }

    /**
     * 一次读取多个字节
     * @throws Exception
     */
    public static void test_02() throws Exception{
        // 1 创建文件输入流
        FileInputStream fis = new FileInputStream("src/main/resources/io/data_demo_01.txt");

        // 2 读取文件
        byte[] buf = new byte[3];
        int count = 0;
        while((count = fis.read(buf)) != -1){
            System.out.println(new String(buf, 0, count));
        }
        // 3 关闭
        fis.close();
    }
}
