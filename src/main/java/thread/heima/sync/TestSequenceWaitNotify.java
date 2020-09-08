package thread.heima.sync;

public class TestSequenceWaitNotify {
    public static void main(String[] args) {
        SyncWaitNotify swn = new SyncWaitNotify(1, 5);

        new Thread(() -> {
            swn.print(1, 2, "a");
        }).start();
        new Thread(() -> {
            swn.print(2, 3, "b");
        }).start();
        new Thread(() -> {
            swn.print(3, 1, "c");
        }).start();
    }
}


class SyncWaitNotify{
    private int flag;
    private int loopNumber;
    public SyncWaitNotify(int flag, int loopNumber){
        this.flag = flag;
        this.loopNumber = loopNumber;
    }
    public void print(int curFlag, int nextFlag, String msg){
        for(int i = 0; i < loopNumber; ++i){
            synchronized (this){
                while(this.flag != curFlag){
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(msg);
                flag = nextFlag;
                this.notifyAll();
            }
        }
    }
}