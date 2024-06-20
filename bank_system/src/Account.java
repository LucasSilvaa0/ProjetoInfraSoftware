import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private final ReentrantLock lock = new ReentrantLock();
    private double balance;

    public Account(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) throws Exception {
        if (amount < 0) {
            throw new Exception("Invalid amount");
        }

        Thread.sleep(1000);

        String threadData = "[DEPOSIT $" + amount + " - " + Thread.currentThread().getName() + " #" + Thread.currentThread().threadId() + "]";

        this.lock.lock();
        System.out.println(threadData + " Initial balance: " + this.balance);

        this.balance += amount;

        System.out.println(threadData + " Final balance: " + this.balance);
        this.lock.unlock();
    }

    public void withdraw(double amount) throws Exception {
        String threadData = "[WITHDRAW $" + amount + " - " + Thread.currentThread().getName() + " #" + Thread.currentThread().threadId() + "]";
        Thread.sleep(1000);

        this.lock.lock();
        System.out.println(threadData + " Initial balance: " + this.balance);

        if (amount > this.balance) {
            this.lock.unlock();
            throw new Exception(threadData + " Insufficient balance");
        }

        this.balance -= amount;

        System.out.println(threadData + " Final balance: " + this.balance);
        this.lock.unlock();
    }
}
