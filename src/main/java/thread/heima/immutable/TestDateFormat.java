package thread.heima.immutable;

import lombok.extern.slf4j.Slf4j;

import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
public class TestDateFormat {
    public static void main(String[] args) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for(int i = 0; i < 10; ++i){
            new Thread(() -> {
                log.debug("{}", dtf.parse("1951-04-21"));
            }, "t" + (i+1)).start();
        }
    }

    public static void test_01(){
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");

        for(int i = 0; i < 10; ++i){
            new Thread(() -> {
                synchronized (sdf){
                    try {
                        log.debug("{}", sdf.parse("1951-04-21"));
                    } catch (ParseException e) {
                        log.error("{}", e);
                    }
                }
            }, "t" + (i+1)).start();
        }
    }
}
