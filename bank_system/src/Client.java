import java.util.concurrent.CountDownLatch;

public class Client {
    private final Account account;
    private final CountDownLatch latch;
    public String name;

    public Client(String name, Account familyAccount, CountDownLatch latch) {
        this.name = name;
        this.account = familyAccount;
        this.latch = latch;
    }

    public void withdraw(double amount) {
        WithdrawTask task = new WithdrawTask(this, this.account, amount, this.latch);
        Thread thread = new Thread(task);
        thread.start();
    }

    public void deposit(double amount) {
        DepositTask task = new DepositTask(this, this.account, amount, latch);
        Thread thread = new Thread(task);
        thread.start();
    }

}

abstract class ClientTask implements Runnable {
    CountDownLatch latch;
    Client client;
    Account account;
    double amount;

    public ClientTask(Client client, Account account, double amount, CountDownLatch latch) {
        this.client = client;
        this.account = account;
        this.amount = amount;
        this.latch = latch;
    }

    @Override
    public abstract void run();
}

class WithdrawTask extends ClientTask {
    public WithdrawTask(Client client, Account account, double amount, CountDownLatch latch) {
        super(client, account, amount, latch);
    }

    public void run() {
        try {
            System.out.println(this.client.name + " is trying to withdraw " + this.amount);
            this.account.withdraw(this.amount);
            System.out.println(this.client.name + " withdrew " + this.amount);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (this.latch != null) {
                this.latch.countDown();
                System.out.println("Latch count: " + this.latch.getCount());
            }
        }
    }
}

class DepositTask extends ClientTask {
    public DepositTask(Client client, Account account, double amount, CountDownLatch latch) {
        super(client, account, amount, latch);
    }

    public void run() {
        try {
            System.out.println(this.client.name + " is trying to deposit " + this.amount);
            this.account.deposit(this.amount);
            System.out.println(this.client.name + " deposited " + this.amount);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (this.latch != null) {
                this.latch.countDown();
                System.out.println("Latch count: " + this.latch.getCount());
            }
        }
    }
}
