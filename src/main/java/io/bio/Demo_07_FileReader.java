package io.bio;

import java.io.FileReader;

public class Demo_07_FileReader {
    public static void main(String[] args) throws Exception {
        readOneCharacter();
        readCharacters();
    }

    public static void readOneCharacter() throws Exception{
        FileReader reader = new FileReader("src/main/resources/io/data_demo_07.txt");
        int data;
        while((data = reader.read()) != -1){
            System.out.println((char)data);
        }
        reader.close();
    }

    public static void readCharacters() throws Exception{
        FileReader reader = new FileReader("src/main/resources/io/data_demo_07.txt");

        int count;
        char[] buf = new char[2];
        while((count = reader.read(buf)) != -1){
            System.out.println(new String(buf, 0, count));
        }
        reader.close();
    }
}
