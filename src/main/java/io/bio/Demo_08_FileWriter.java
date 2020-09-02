package io.bio;

import java.io.FileWriter;

public class Demo_08_FileWriter {
    public static void main(String[] args) throws Exception {
        write();
    }

    public static void write() throws Exception{
        FileWriter writer = new FileWriter("src/main/resources/io/data_demo_09.txt");
        writer.write("Java大法好");
        writer.close();
    }
}
