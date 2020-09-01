package aio;

import javax.xml.ws.soap.Addressing;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;

public class AIOClient {
    AsynchronousSocketChannel asc = null;
    Future<?> future = null;
    public static void main(String[] args) throws Exception {
        AIOClient client = new AIOClient("127.0.0.1", 9898);
        client.write("Hello!");
        client.close();
    }

    public AIOClient(String ip, int port) throws Exception {
        asc = AsynchronousSocketChannel.open();
        future = asc.connect(new InetSocketAddress(ip, port));
        future.get();
        //System.out.println(result.isDone());
    }

    public void write(String str) throws Exception{
        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.put(str.getBytes());
        buf.flip();
        asc.write(buf);
        buf.clear();

        asc.shutdownOutput();
        if(asc.read(buf).get() != -1){
            buf.flip();
            System.out.println(new String(buf.array(), 0, buf.limit()));
            buf.clear();
        }
    }

    public void close()throws Exception{
        asc.close();
    }
}
