package thread.heima.immutable;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class TestConnectionPool {
    public static void main(String[] args) {
        Pool pool = new Pool(2);

        for(int i = 0; i < 5; ++i){
            new Thread(() -> {
                MockConnection connection =  pool.borrow();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pool.free(connection);
            }, "Thread " + (i + 1)).start();
        }
    }
}
@Slf4j
class Pool {
    // ��������̳߳�
    private final int poolSize;

    // ������������
    private MockConnection[] connections;

    // ����������״̬������
    private AtomicIntegerArray states;

    // ���캯��
    public Pool(int poolSize) {
        this.poolSize = poolSize;
        this.connections = new MockConnection[poolSize];
        this.states = new AtomicIntegerArray(new int[poolSize]);
        for(int i = 0; i < poolSize; ++i){
            connections[i] = new MockConnection("����" + (i+1));
        }

    }

    // ������
    public MockConnection borrow(){
        while(true){
            for(int i = 0; i < poolSize; ++i){
                if(states.get(i) == 0){
                    if(states.compareAndSet(i,0, 1)){
                        log.debug("borrow {}", connections[i]);
                        return  connections[i];
                    }
                }
            }
            // û�п����̣߳���ǰ�߳̽���ȴ�
            synchronized (this){
                try {
                    log.debug("wait...");
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    // �黹����

    public void free(MockConnection connection){
        for(int i = 0; i < poolSize; ++i){
            if(connections[i] == connection){
                states.set(i, 0);
                synchronized (this){
                    log.debug("free {}", connection);
                    this.notifyAll();
                }
                break;
            }
        }

    }
}
class MockConnection {
    private String name;

    public MockConnection(String name) {
        this.name = name;
    }
}

