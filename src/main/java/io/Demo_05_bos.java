package io;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

public class Demo_05_bos {
    public static void main(String[] args) throws Exception {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("src/main/resources/io/data_demo_05.txt"));

        for(int i = 0; i < 10; ++i){
            bos.write("Hello BufferedOutputStream\r\n".getBytes());
            // bos.flush();
        }
        bos.close();
    }
}
