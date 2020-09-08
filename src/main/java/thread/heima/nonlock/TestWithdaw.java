package thread.heima.nonlock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TestWithdaw {
    public static void main(String[] args) {
        Account accountUnsafe = new AccountUnsafe(10000);
        Account accountUnsafe1 = new AccountSafe1(10000);
        Account accountSafe2 = new AccountSafe2(10000);
        Account.demo(accountUnsafe);
        Account.demo(accountUnsafe1);
        Account.demo(accountSafe2);
    }
}



interface Account {
    // ��ȡ���
    Integer getBalance();
    // ȡ��
    void withdraw(Integer amount);
    /**
     * �����ڻ����� 1000 ���̣߳�ÿ���߳��� -10 Ԫ �Ĳ���
     * �����ʼ���Ϊ 10000 ��ô��ȷ�Ľ��Ӧ���� 0
     */
    static void demo(Account account) {
        List<Thread> ts = new ArrayList<>();
        long start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(() -> {
                account.withdraw(10);
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
        long end = System.nanoTime();
        System.out.println(account.getBalance()
                + " cost: " + (end-start)/1000_000 + " ms");
    }
}

class AccountUnsafe implements Account {
    private Integer balance;
    public AccountUnsafe(Integer balance) {
        this.balance = balance;
    }
    @Override
    public Integer getBalance() {
        return balance;
    }
    @Override
    public void withdraw(Integer amount) {
        balance -= amount;
    }
}

class AccountSafe1 implements Account {
    private Integer balance;
    public AccountSafe1(Integer balance) {
        this.balance = balance;
    }
    @Override
    public synchronized Integer getBalance() {
        return balance;
    }
    @Override
    public synchronized void withdraw(Integer amount) {
        balance -= amount;
    }
}

class AccountSafe2 implements Account {
    private AtomicInteger balance;
    public AccountSafe2(Integer balance) {
        this.balance = new AtomicInteger(balance);
    }
    @Override
    public Integer getBalance() {
        return balance.get();
    }
    @Override
    public void withdraw(Integer amount) {
        while (true) {
            // ��ȡ����ֵ
            int prev = balance.get();
            int next = prev - amount;
            if (balance.compareAndSet(prev, next)) {
                break;
            }
        }
    }
}