package nio;

import org.junit.Test;

import java.nio.ByteBuffer;

public class TestBuffer {
    /**
     * 分配直接缓冲区
     */
    @Test
    public void test_03(){
        ByteBuffer buf = ByteBuffer.allocateDirect(1024);
        System.out.println(buf.isDirect());
    }

    // 测试 mark
    @Test
    public void test_02(){
        ByteBuffer bf = ByteBuffer.allocate(1024);

        bf.put("ashdsafaf".getBytes());

        bf.flip();

        byte[] dst = new byte[bf.limit()];

        bf.get(dst, 0, 2);
        System.out.println(new String(dst));
        bf.mark();
        bf.get(dst, 2, 2);
        System.out.println(new String(dst, 2, 2));

        // reset
        System.out.println(bf.position());
        bf.reset();
        System.out.println(bf.position());
    }

    /**
     * 测试缓冲区得读写
     */
    @Test
    public void test_01(){
        ByteBuffer bf = ByteBuffer.allocate(1024);
        // printStatus(bf);
        write(bf, "abcde");

        // 3. 读数据
        bf.flip();
        printStatus(bf);
        byte[] dst = new byte[bf.limit()];
        bf.get(dst);
        System.out.println(new String(dst));
        printStatus(bf);

        // 4. 重新恢复缓冲区
        bf.rewind();
        printStatus(bf);
        dst = new byte[bf.limit()];
        bf.get(dst);
        System.out.println(new String(dst));
        printStatus(bf);

        // 5 清空缓冲区
        bf.clear();
    }

    /**
     * 读取缓冲区状态
     * @param bf
     */
    public void printStatus(ByteBuffer bf){
        System.out.println(bf.capacity());
        System.out.println(bf.limit());
        System.out.println(bf.position());
    }

    /**
     * 向缓冲区中写数据
     * @param bf
     */
    public void write(ByteBuffer bf, String str){
        bf.put(str.getBytes());
        // printStatus(bf);
    }
}
