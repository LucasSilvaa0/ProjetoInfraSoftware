import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(7);

        Account account = new Account(1000);
        Client client1 = new Client("Alice", account, latch);
        Client client2 = new Client("Bob", account, latch);
        Client client3 = new Client("Charlie", account, latch);
        Client client4 = new Client("David", account, latch);

        client1.withdraw(1000);
        client2.deposit(10000);
        client1.withdraw(12000);
        client2.deposit(200);
        client3.withdraw(100);
        client4.deposit(1000);
        client1.deposit(100);

        try {
            latch.await();
            System.out.println("------------------------------");
            System.out.println("Final balance: " + account.getBalance());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
