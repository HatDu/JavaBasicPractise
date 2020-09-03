package io.nio.block;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {
    public Server(int port) throws Exception{
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(port));

        SocketChannel sc = ssc.accept();

        ByteBuffer buf = ByteBuffer.allocate(1024);

        while(sc.read(buf) != -1){
            // ����
            buf.flip();
            System.out.println(new String(buf.array(), 0, buf.limit()));
            buf.clear();

            // �ظ���Ϣ
            buf.put("��Ϣ���յ�".getBytes());
            buf.flip();
            sc.write(buf);
            buf.clear();
        }

        sc.close();
        ssc.close();
    }

    public static void main(String[] args) throws Exception{
        Server server = new Server(7890);
    }
}
