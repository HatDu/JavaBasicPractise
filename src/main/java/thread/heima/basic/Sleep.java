package thread.heima.basic;

public class Sleep {
    public static void sleepMS(long ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
