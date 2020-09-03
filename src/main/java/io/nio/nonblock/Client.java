package io.nio.nonblock;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Scanner;

public class Client {
    private String name;
    public Client(String ip, int port) throws Exception{
        System.out.print("输入客户端名称：");
        SocketChannel sc = SocketChannel.open(new InetSocketAddress(ip, port));
        Scanner scanner = new Scanner(System.in);
        name = scanner.nextLine();

        System.out.print("发送：");

        while(scanner.hasNext()){
            String line = scanner.nextLine();
            String msg = new Date().toString() + "\n" + name + ": " + line;
            ByteBuffer buf = ByteBuffer.allocate(1024);
            buf.put(msg.getBytes());
            buf.flip();
            sc.write(buf);
            buf.clear();

            if(sc.read(buf) != -1){
                buf.flip();
                System.out.println("接收：" + new String(buf.array(), 0, buf.limit()));
                buf.clear();
            }

            System.out.print("发送：");

        }

        sc.close();
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client("127.0.0.1", 7890);
    }
}
