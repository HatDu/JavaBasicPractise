package io.bio;

import java.io.*;

/**
 * 字节流与字符流转换
 */
public class Demo_10_convert {
    public static void main(String[] args) throws Exception {
        write();
        read();
    }

    public static void read() throws Exception{
        FileInputStream fis = new FileInputStream("src/main/resources/io/data_demo_10.txt");
        InputStreamReader isr = new InputStreamReader(fis, "gbk"); // utf-8
        BufferedReader br = new BufferedReader(isr);
        String line = null;
        while((line = br.readLine()) != null){
            System.out.println(line);
        }
        br.close();
        isr.close();
        fis.close();
    }

    public static void write() throws Exception{
        FileOutputStream fos = new FileOutputStream("src/main/resources/io/data_demo_10.txt");
        OutputStreamWriter osw = new OutputStreamWriter(fos, "gbk");
        BufferedWriter bw = new BufferedWriter(osw);
        for(int i = 0; i < 10; ++i){
            bw.write("Java大法好!\n");
        }
        bw.close();
        osw.close();
        fos.close();
    }
}
