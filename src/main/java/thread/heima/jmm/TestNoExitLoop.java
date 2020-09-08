package thread.heima.jmm;

public class TestNoExitLoop {
    static boolean run = true;
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(()->{
            while(run){
                System.out.println("2333");
            }
        });
        t.start();
        Thread.sleep(1000);
        run = false;
    }
}
