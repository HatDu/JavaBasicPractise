package nio;

import jdk.management.resource.internal.inst.FileChannelImplRMHooks;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TestBlock2 {
    @Test
    public void client() throws Exception{
        SocketChannel sc = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

        String inPath = "src/main/resources/nio/wangzai_3.png";
        FileChannel inChannel = FileChannel.open(Paths.get(inPath), StandardOpenOption.READ);

        ByteBuffer buf = ByteBuffer.allocate(1024);

        while(inChannel.read(buf) != -1){
            buf.flip();
            sc.write(buf);
            buf.clear();
        }
        sc.shutdownOutput();

        while(sc.read(buf) != -1){
            buf.flip();
            System.out.println("客户端：" + new String(buf.array(), 0, buf.limit()));
            buf.clear();
        }
        inChannel.close();
        sc.close();
    }

    @Test
    public void server() throws Exception{
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(9898));

        SocketChannel sc = ssc.accept();

        String outPath = "C:\\Users\\dnm\\Desktop\\tmp\\block2.png";
        FileChannel outChannel = FileChannel.open(Paths.get(outPath), StandardOpenOption.CREATE, StandardOpenOption.WRITE);

        ByteBuffer buf = ByteBuffer.allocate(1024);
        while(sc.read(buf) != -1){
            buf.flip();
            outChannel.write(buf);
            buf.clear();
        }
        outChannel.close();
        // 服务器端发送消息
        buf.put("图片接收成功！".getBytes());
        buf.flip();
        sc.write(buf);
        sc.close();
        ssc.close();
    }
}
