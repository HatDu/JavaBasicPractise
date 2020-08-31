package nio;

import org.junit.Test;

import java.nio.ByteBuffer;

public class TestBuffer {
    /**
     * ����ֱ�ӻ�����
     */
    @Test
    public void test_03(){
        ByteBuffer buf = ByteBuffer.allocateDirect(1024);
        System.out.println(buf.isDirect());
    }

    // ���� mark
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
     * ���Ի������ö�д
     */
    @Test
    public void test_01(){
        ByteBuffer bf = ByteBuffer.allocate(1024);
        // printStatus(bf);
        write(bf, "abcde");

        // 3. ������
        bf.flip();
        printStatus(bf);
        byte[] dst = new byte[bf.limit()];
        bf.get(dst);
        System.out.println(new String(dst));
        printStatus(bf);

        // 4. ���»ָ�������
        bf.rewind();
        printStatus(bf);
        dst = new byte[bf.limit()];
        bf.get(dst);
        System.out.println(new String(dst));
        printStatus(bf);

        // 5 ��ջ�����
        bf.clear();
    }

    /**
     * ��ȡ������״̬
     * @param bf
     */
    public void printStatus(ByteBuffer bf){
        System.out.println(bf.capacity());
        System.out.println(bf.limit());
        System.out.println(bf.position());
    }

    /**
     * �򻺳�����д����
     * @param bf
     */
    public void write(ByteBuffer bf, String str){
        bf.put(str.getBytes());
        // printStatus(bf);
    }
}
