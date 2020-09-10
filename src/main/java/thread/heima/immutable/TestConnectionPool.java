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
    // 定义最大线程池
    private final int poolSize;

    // 定义连接数组
    private MockConnection[] connections;

    // 定义标记连接状态的数组
    private AtomicIntegerArray states;

    // 构造函数
    public Pool(int poolSize) {
        this.poolSize = poolSize;
        this.connections = new MockConnection[poolSize];
        this.states = new AtomicIntegerArray(new int[poolSize]);
        for(int i = 0; i < poolSize; ++i){
            connections[i] = new MockConnection("连接" + (i+1));
        }

    }

    // 借连接
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
            // 没有可用线程，当前线程进入等待
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
    // 归还连接

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

