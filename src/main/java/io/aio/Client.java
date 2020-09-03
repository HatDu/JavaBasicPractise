package io.aio;

import com.sun.javafx.image.ByteToBytePixelConverter;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Client {
    AsynchronousSocketChannel asc;
    public Client() throws Exception{
        asc = AsynchronousSocketChannel.open();
    }

    public void connect(String ip, int port){
        asc.connect(new InetSocketAddress(ip, port));
    }

    public void send(String msg){
        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.put(msg.getBytes());
        buf.flip();
        asc.write(buf);

        read();
    }

    public void read(){
        ByteBuffer buf = ByteBuffer.allocate(1024);
        try {
            if(asc.read(buf).get() != -1){
                buf.flip();
                System.out.println("接收：" + new String(buf.array(), 0, buf.limit()));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.connect("127.0.0.1", 7890);

        Scanner scanner = new Scanner(System.in);

        System.out.print("输入客户端名称：");
        String name = scanner.nextLine();
        System.out.print("发送：");
        while(scanner.hasNext()){
            String line = scanner.nextLine();
            String msg = new Date().toString() + "\n" + name + ": " +line;
            client.send(msg);
            System.out.print("发送：");
        }
    }
}
