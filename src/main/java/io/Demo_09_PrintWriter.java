package io;

import java.io.PrintWriter;

public class Demo_09_PrintWriter {
    public static void main(String[] args) throws Exception {
        PrintWriter pw = new PrintWriter("src/main/resources/io/data_demo_09.txt");

        pw.println("23461");
        pw.println("天天向上");
        pw.println(3.1415926);
        pw.close();
    }
}
