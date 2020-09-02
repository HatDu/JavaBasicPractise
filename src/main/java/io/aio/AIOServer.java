package io.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class AIOServer {

    public static void main(String[] args) throws Exception {
        AIOServer aioServer = new AIOServer(9898);
        System.out.println("服务端监听");
        Thread.sleep(1000000);
    }
    public AIOServer(int port) throws Exception{
        AsynchronousServerSocketChannel assc = AsynchronousServerSocketChannel.open();
        assc.bind(new InetSocketAddress(port));
        assc.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {

            @Override
            public void completed(AsynchronousSocketChannel result, Object attachment) {
                // 继续接收下一个连接
                assc.accept(null, this);
                serverHandler(result);
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("异步IO失败");
            }
        });
    }

    public void serverHandler(AsynchronousSocketChannel ch){
        try {
            ByteBuffer buf = ByteBuffer.allocate(1024);
            if(ch.read(buf).get() != -1){
                buf.flip();
                System.out.println(new Date().toString() + "\n" + new String(buf.array(), 0, buf.limit()));
                buf.clear();
            }

            // 回复
            buf.put("你好，我已经收到消息".getBytes());
            buf.flip();
            ch.write(buf);
            buf.clear();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
