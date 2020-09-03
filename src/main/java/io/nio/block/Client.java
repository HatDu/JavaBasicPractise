package io.nio.block;

import javax.xml.crypto.Data;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Scanner;

public class Client {
    private String name;
    public Client(String _name, String ip, int port) throws Exception{
        name = _name;
        SocketChannel sc = SocketChannel.open(new InetSocketAddress(ip, port));
        Scanner scanner = new Scanner(System.in);

        ByteBuffer buf = ByteBuffer.allocate(1024);
        System.out.print("发送：");
        while(scanner.hasNext()){
            // 发送消息
            String line = scanner.nextLine();
            String msg = new Date().toString() + "\n" + name + ": " + line;
            buf.put(msg.getBytes());
            buf.flip();
            sc.write(buf);
            buf.clear();

            // 接收消息
            if(sc.read(buf) != -1){
                buf.flip();
                System.out.println("接收：" + new String(buf.array(), 0, buf.limit()));
                buf.clear();
            }

            System.out.print("发送：");
        }
    }
    public static void main(String[] args) throws Exception{
        System.out.print("请输入连接名称：");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        Client client = new Client(name, "127.0.0.1", 7890);
    }
}
