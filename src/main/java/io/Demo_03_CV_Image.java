package io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Demo_03_CV_Image {
    public static void main(String[] args) {
        Demo_03_CV_Image s3 = new Demo_03_CV_Image();
        s3.cv("C:\\Users\\dnm\\Desktop\\640.webp", "src/main/resources/io/data_demo_03.webp");
    }
    public void cv(String inPath, String outPath){
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(inPath);
            fos = new FileOutputStream(outPath);
            byte[] buf = new byte[1024];
            int count = 0;
            while((count = fis.read(buf)) != -1){
                fos.write(buf, 0, count);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
