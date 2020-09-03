package io.aio;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    // 线程池
    private ExecutorService executorService;
    // 连接池
    private AsynchronousChannelGroup acGroup;
    // 服务器通道
    private AsynchronousServerSocketChannel assc;

    public Server(int port) throws Exception{
        executorService = Executors.newCachedThreadPool();
        acGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService, 1);
        assc = AsynchronousServerSocketChannel.open();

        // 绑定端口号
        assc.bind(new InetSocketAddress(port));
        System.out.println("服务端监听端口：" + port);

        // 进行阻塞
        assc.accept(this, new MyAioCompletionHandler());

        // 一直阻塞，不让服务器停止
        Thread.sleep(Integer.MAX_VALUE);
    }

    public AsynchronousServerSocketChannel getAssc() {
        return assc;
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server(7890);
    }
}

class MyAioCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Server> {

    @Override
    public void completed(AsynchronousSocketChannel result, Server attachment) {
        attachment.getAssc().accept(attachment, this);
        read(result);
    }

    private void read(AsynchronousSocketChannel asc) {
        ByteBuffer buf = ByteBuffer.allocate(1024);
        asc.read(buf, buf, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                attachment.flip();
                System.out.println(new String(attachment.array(), 0, attachment.limit()));
                write(asc, "你好，已接收到消息");
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                exc.printStackTrace();
            }

            public void write(AsynchronousSocketChannel asc, String response){
                ByteBuffer buf = ByteBuffer.allocate(1024);
                buf.put(response.getBytes());
                buf.flip();
                try {
                    asc.write(buf).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void failed(Throwable exc, Server attachment) {

    }
}
