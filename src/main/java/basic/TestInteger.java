package basic;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

public class TestInteger {
    public static void main(String[] args) {
        Integer a = new Integer(5);
        Integer b = new Integer(5);

        System.out.println(a == b);

        Integer c = 5;
        Integer d = 5;
        System.out.println(c == d);

        int h = 5;

        System.out.println(a == h);
        System.out.println(d == h);


        Integer e = 128;
        Integer f = 128;

        System.out.println(e == f);


        Integer g = (int)a;
        System.out.println(g == c);
        System.out.println(g == a);
        // ���ۣ�ʹ��new���������Ķ������ڴ��ж��ж����Ŀռ�
        // ʹ�ó�������ǿת��[-128, 127]�����и�ֵʱ,����ĵ�ַ����ͬ
    }
}
