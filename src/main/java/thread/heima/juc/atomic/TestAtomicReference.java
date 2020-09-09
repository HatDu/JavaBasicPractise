package thread.heima.juc.atomic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TestAtomicReference {
    public static void main(String[] args) {
        DecimalAccount account = new DecimalAccountSafe(new BigDecimal("10000"));
        DecimalAccount.demo(account);
    }
}


interface DecimalAccount{
    BigDecimal getBalance();

    void withdraw(BigDecimal amount);

    static void demo(DecimalAccount account){
        List<Thread> ts = new ArrayList<>();
        for(int i = 0; i < 1000; ++i){
            ts.add(new Thread(() -> {
                account.withdraw(BigDecimal.TEN);
            }));
        }

        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(account.getBalance());
    }
}

class DecimalAccountSafe implements DecimalAccount{
    AtomicReference<BigDecimal> balance;

    public DecimalAccountSafe(BigDecimal balance) {
        this.balance = new AtomicReference<>(balance);
    }

    @Override
    public BigDecimal getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(BigDecimal amount) {
        while(true){
            BigDecimal prev = balance.get();
            if(balance.compareAndSet(prev, prev.subtract(amount))){
                break;
            }
        }
    }
}