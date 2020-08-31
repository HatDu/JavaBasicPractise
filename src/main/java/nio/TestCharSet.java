package nio;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Map;
import java.util.Set;

public class TestCharSet {
    @Test
    public void test_1(){
        Map<String, Charset> map = Charset.availableCharsets();

        Set<Map.Entry<String, Charset>> set = map.entrySet();

        for(Map.Entry<String, Charset> entry : set){
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }

    @Test
    public void test_2() throws Exception{
        // 获取字符集
        Charset cs1 = Charset.forName("GBK");
        // 获取编码器与解码器
        CharsetEncoder ce = cs1.newEncoder();

        // 准备字符缓冲区写入数据
        CharBuffer cbuf = CharBuffer.allocate(1024);
        cbuf.put("Java大法好！");
        cbuf.flip();

        // 编码
        ByteBuffer bbuf = ce.encode(cbuf);
        for(int i = 0; i < bbuf.limit(); ++i){
            System.out.print(bbuf.get());
        }
        System.out.println();

        Charset cs2 = Charset.forName("UTF-8");
        // 解码
        bbuf.flip();
        CharBuffer debuf = cs2.decode(bbuf);
        //debuf.flip();

        System.out.println(debuf.toString());
    }
}
