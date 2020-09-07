package thread.heima.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

public class TestDeadLockPhilosopher {
    public static void main(String[] args) {
        //test1();
        test2();
    }

    public static void test1(){
        Chopstick chopstick1 = new Chopstick("1");
        Chopstick chopstick2 = new Chopstick("2");
        Chopstick chopstick3 = new Chopstick("3");
        Chopstick chopstick4 = new Chopstick("4");
        Chopstick chopstick5 = new Chopstick("5");

        new Philosopher("苏格拉底", chopstick1, chopstick2).start();
        new Philosopher("柏拉图", chopstick2, chopstick3).start();
        new Philosopher("亚里士多德", chopstick3, chopstick4).start();
        new Philosopher("赫拉克利特", chopstick4, chopstick5).start();
        new Philosopher("阿基米德", chopstick5, chopstick1).start();
    }
    // 顺序加锁
    public static void test2(){
        Chopstick chopstick1 = new Chopstick("1");
        Chopstick chopstick2 = new Chopstick("2");
        Chopstick chopstick3 = new Chopstick("3");
        Chopstick chopstick4 = new Chopstick("4");
        Chopstick chopstick5 = new Chopstick("5");

        new Philosopher("苏格拉底", chopstick1, chopstick2).start();
        new Philosopher("柏拉图", chopstick2, chopstick3).start();
        new Philosopher("亚里士多德", chopstick3, chopstick4).start();
        new Philosopher("赫拉克利特", chopstick4, chopstick5).start();
        new Philosopher("阿基米德", chopstick1, chopstick5).start();
    }
}

@Slf4j
class Philosopher extends Thread{
    Chopstick left;
    Chopstick right;

    public Philosopher(String name, Chopstick left, Chopstick right) {
        super(name);
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        while (true){
            if(left.tryLock()){
                try {
                    if(right.tryLock()){
                        try{
                            eat();
                        }finally {
                            right.unlock();
                        }
                    }
                }finally {
                    left.unlock();
                }
            }
        }
    }

    private void eat(){
        log.debug("eating...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Chopstick extends ReentrantLock {
    String name;

    @Override
    public String toString() {
        return "Chopstick{" +
                "name='" + name + '\'' +
                '}';
    }

    public Chopstick(String name) {
        this.name = name;
    }
}
