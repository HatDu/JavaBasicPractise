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
        // 1. 创建服务器端socket，并绑定端口
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(9898));

        // 2. 开启非阻塞模式
        ssc.configureBlocking(false);

        // 3. 天剑selector,注册监听事件
        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        // 4. 开始轮询选择器上准备就绪的事件，在没有连接时会阻塞，一旦有连接进入将会无限循环查询
        while(selector.select() > 0){
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            // System.out.println("共侦听" + selector.selectedKeys().size() + "个连接");

            while (it.hasNext()){
                SelectionKey sk = it.next();
                // 选取有数据的连接，将其注册到选择器上
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
            System.out.println("客户端接收消息：" + new String(buf.array(), 0, buf.limit()));
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
                    buf.put("收到消息，你好啊。".getBytes());
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
