package nio;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Timer;

public class TestChannel {

    /**
     * 1. 利用通道完成文件的复制
     */
    @Test
    public void test1() throws IOException {
        Long start = System.currentTimeMillis();
        String srcPath = "D:\\BDown\\bilidown\\Re：从零开始的异世界生活 新编集版\\第1话 初始的终结与结束的开始.flv";
        String targetPath ="C:\\Users\\dnm\\Desktop\\tmp\\第1话 初始的终结与结束的开始.flv";
        FileInputStream fis = new FileInputStream(srcPath);
        FileOutputStream fos = new FileOutputStream(targetPath);

        FileChannel inChannel = fis.getChannel();
        FileChannel outChannel = fos.getChannel();

        // ByteBuffer buf = ByteBuffer.allocate(1024);
        ByteBuffer buf = ByteBuffer.allocateDirect(1024);

        while(inChannel.read(buf) != -1){
            buf.flip();
            outChannel.write(buf);
            buf.clear();
        }
        outChannel.close();
        inChannel.close();
        fos.close();
        fis.close();
        Long end = System.currentTimeMillis();
        // 18468ms 18570ms
        System.out.println("文件耗时为：" + (end - start) + "ms");
    }

    /**
     * 2. 使用直接缓冲区完成文件的复制
     */
    @Test
    public void test2() throws IOException {

        Long start = System.currentTimeMillis();
        String srcPath = "D:\\BDown\\bilidown\\Re：从零开始的异世界生活 新编集版\\第1话 初始的终结与结束的开始.flv";
        String targetPath ="C:\\Users\\dnm\\Desktop\\tmp\\第1话 初始的终结与结束的开始.flv";
        FileChannel inChannel = FileChannel.open(Paths.get(srcPath), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get(targetPath), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        // 内存映射文件
        MappedByteBuffer inMappedBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMappedBuf = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

        byte[] dst = new byte[inMappedBuf.limit()];
        inMappedBuf.get(dst);
        outMappedBuf.put(dst);

        outChannel.close();
        inChannel.close();
        Long end = System.currentTimeMillis();
        // 文件耗时为：3676 4046
        System.out.println("文件耗时为：" + (end - start) + "ms");
    }

    /**
     * 3. FileChannel.transformTo
     */
    @Test
    public void test3() throws IOException {
        Long start = System.currentTimeMillis();
        String srcPath = "D:\\BDown\\bilidown\\Re：从零开始的异世界生活 新编集版\\第1话 初始的终结与结束的开始.flv";
        String targetPath ="C:\\Users\\dnm\\Desktop\\tmp\\第1话 初始的终结与结束的开始.flv";
        FileChannel inChannel = FileChannel.open(Paths.get(srcPath), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get(targetPath), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        // inChannel.transferTo(0, inChannel.size(), outChannel);
        outChannel.transferFrom(inChannel, 0, inChannel.size());
        outChannel.close();
        inChannel.close();
        Long end = System.currentTimeMillis();
        System.out.println("文件耗时为：" + (end - start) + "ms");
    }

    @Test
    public void test4() throws Exception{
        Long start = System.currentTimeMillis();
        String srcPath = "D:\\BDown\\bilidown\\Re：从零开始的异世界生活 新编集版\\第1话 初始的终结与结束的开始.flv";
        String targetPath ="C:\\Users\\dnm\\Desktop\\tmp\\第1话 初始的终结与结束的开始.flv";
        FileInputStream fis = new FileInputStream(srcPath);
        FileOutputStream fos = new FileOutputStream(targetPath);


        FileChannel inChannel = fis.getChannel();
        FileChannel outChannel = fos.getChannel();

        ByteBuffer[] bufs = new ByteBuffer[100];
        for(int i = 0; i < bufs.length; ++i){
            bufs[i] = ByteBuffer.allocate(1024*1024*20);
        }
        inChannel.read(bufs);

        for(ByteBuffer buf : bufs){
            buf.flip();
        }
        outChannel.write(bufs);
        outChannel.close();
        inChannel.close();
        Long end = System.currentTimeMillis();
        System.out.println("文件耗时为：" + (end - start) + "ms");
    }
}
