package io.nio.nonblock;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.security.PublicKey;
import java.util.Iterator;

public class Server {

    public Server(int port) throws Exception{
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(port));
        ssc.configureBlocking(false);

        Selector selector = Selector.open();

        ssc.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("开始侦听...");
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

                    // 接收
                    if(sc.read(buf) > 0){
                        buf.flip();
                        System.out.println(new String(buf.array(), 0, buf.limit()));
                        buf.clear();
                    }
                    sc.shutdownInput();
                    // 回复
                    buf.put("收到消息，你好啊。".getBytes());
                    buf.flip();
                    sc.write(buf);
                }
                it.remove();
            }
        }
    }

    public static void main(String[] args) throws Exception{
        Server server = new Server(7890);
    }
}
