package nio;

import com.sun.security.ntlm.Server;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.io.File;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

public class TestNonBlocking {
    @Test
    public void client() throws Exception{
        SocketChannel sc = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

        sc.configureBlocking(false);

        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.put((new Date().toString()).getBytes());
        buf.flip();
        sc.write(buf);
        buf.clear();
        sc.close();
    }

    @Test
    public void server() throws Exception{
        // 1. ������������socket�����󶨶˿�
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(9898));

        // 2. ����������ģʽ
        ssc.configureBlocking(false);

        // 3. �콣selector,ע������¼�
        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        // 4. ��ʼ��ѯѡ������׼���������¼�����û������ʱ��������һ�������ӽ��뽫������ѭ����ѯ
        while(selector.select() > 0){
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            // System.out.println("������" + selector.selectedKeys().size() + "������");

            while (it.hasNext()){
                SelectionKey sk = it.next();
                // ѡȡ�����ݵ����ӣ�����ע�ᵽѡ������
                if(sk.isAcceptable()){
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ);
                }
                else if(sk.isReadable()){
                    SocketChannel sc = (SocketChannel) sk.channel();
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    while(sc.read(buf) > 0){
                        buf.flip();
                        System.out.println(new String(buf.array(), 0, buf.limit()));
                        buf.clear();
                    }
                }
                it.remove();
            }
        }
    }

    @Test
    public void client2() throws Exception{
        SocketChannel sc = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
        sc.configureBlocking(false);

        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.put(new Date().toString().getBytes());
        buf.flip();
        sc.write(buf);
        buf.clear();
        sc.shutdownOutput();

        if(sc.read(buf) != -1){
            buf.flip();
            System.out.println("�ͻ��˽�����Ϣ��" + new String(buf.array(), 0, buf.limit()));
            buf.clear();
        }
        sc.close();
        buf.clear();
    }

    @Test
    public void server2() throws Exception{
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(9898));
        ssc.configureBlocking(false);
        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while(selector.select() > 0){
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while(it.hasNext()){
                SelectionKey sk = it.next();
                if(sk.isAcceptable()){
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ);
                }
                else if(sk.isReadable()){
                    SocketChannel sc = (SocketChannel) sk.channel();
                    ByteBuffer buf = ByteBuffer.allocate(1024);

                    while(sc.read(buf) > 0){
                        buf.flip();
                        System.out.println(new String(buf.array(), 0, buf.limit()));
                        buf.clear();
                    }
                    buf.put("�յ���Ϣ����ð���".getBytes());
                    buf.flip();
                    sc.write(buf);
                    sc.close();
                }
                it.remove();
            }
        }

        ssc.close();
    }
}
