package io.nio;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TestBlock {

    @Test
    public void client() throws Exception{
        // 1. 创建网络通道
        SocketChannel sc = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
        // 2. 创建本地未见通道
        String inPath = "src/main/resources/io.nio/wangzai_3.png";
        FileChannel inChannel = FileChannel.open(Paths.get(inPath), StandardOpenOption.READ);

        // 3. 创建缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        // 4. 数据传输
        while(inChannel.read(buf) != -1){
            buf.flip();
            sc.write(buf);
            buf.clear();
        }

        // 5. 关闭连接
        inChannel.close();
        sc.close();
    }

    @Test
    public void server() throws Exception{
        // 1. 创建服务器网络连接，绑定端口号
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(9898));
        // 2. 开启侦听
        SocketChannel sc = ssc.accept();
        // 3. 创建输出通道
        String outPath = "C:\\Users\\dnm\\Desktop\\tmp\\img.png";
        FileChannel outChannel = FileChannel.open(Paths.get(outPath), StandardOpenOption.CREATE, StandardOpenOption.WRITE);

        // 4. 建立缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        // 4. 接收数据
        while (sc.read(buf) != -1){
            buf.flip();
            outChannel.write(buf);
            buf.clear();
        }
        // 关闭连接
        outChannel.close();
        sc.close();
        ssc.close();
    }
}
