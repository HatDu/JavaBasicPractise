package io.nio;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Date;
import java.util.Iterator;

public class TestDatagram {
    @Test
    public void send() throws Exception{
        DatagramChannel dc = DatagramChannel.open();
        dc.configureBlocking(true);

        ByteBuffer buf = ByteBuffer.allocate(1024);

        buf.put((new Date().toString() + "\nÄãºÃ£¡").getBytes());
        buf.flip();
        SocketAddress target = new InetSocketAddress("127.0.0.1", 9898);
        dc.send(buf, target);
        buf.clear();
        dc.close();
    }

    @Test
    public void receive() throws Exception{
        DatagramChannel dc = DatagramChannel.open();
        dc.configureBlocking(false);
        dc.bind(new InetSocketAddress(9898));

        Selector selector = Selector.open();
        dc.register(selector, SelectionKey.OP_READ);

        while(selector.select() > 0){
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();

            while(it.hasNext()){
                SelectionKey sk = it.next();
                if(sk.isReadable()){
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    dc.receive(buf);
                    buf.flip();
                    System.out.println(new String(buf.array(), 0, buf.limit()));
                    buf.clear();
                }
            }
        }
    }
}
