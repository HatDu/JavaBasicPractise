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
        // 1. ��������ͨ��
        SocketChannel sc = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
        // 2. ��������δ��ͨ��
        String inPath = "src/main/resources/io.nio/wangzai_3.png";
        FileChannel inChannel = FileChannel.open(Paths.get(inPath), StandardOpenOption.READ);

        // 3. ����������
        ByteBuffer buf = ByteBuffer.allocate(1024);

        // 4. ���ݴ���
        while(inChannel.read(buf) != -1){
            buf.flip();
            sc.write(buf);
            buf.clear();
        }

        // 5. �ر�����
        inChannel.close();
        sc.close();
    }

    @Test
    public void server() throws Exception{
        // 1. �����������������ӣ��󶨶˿ں�
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(9898));
        // 2. ��������
        SocketChannel sc = ssc.accept();
        // 3. �������ͨ��
        String outPath = "C:\\Users\\dnm\\Desktop\\tmp\\img.png";
        FileChannel outChannel = FileChannel.open(Paths.get(outPath), StandardOpenOption.CREATE, StandardOpenOption.WRITE);

        // 4. ����������
        ByteBuffer buf = ByteBuffer.allocate(1024);

        // 4. ��������
        while (sc.read(buf) != -1){
            buf.flip();
            outChannel.write(buf);
            buf.clear();
        }
        // �ر�����
        outChannel.close();
        sc.close();
        ssc.close();
    }
}
