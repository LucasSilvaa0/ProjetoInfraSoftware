public class EatTask implements Runnable {
    private final Client client;

    /**
     * Task to simulate a client eating.
     *
     * @param client The client that is eating
     */
    public EatTask(Client client) {
        this.client = client;
    }

    /**
     * Method to simulate a client eating.
     * The client will try to take a seat in the restaurant, eat for 2 seconds and then leave the seat.
     */
    @Override
    public void run() {
        // Take a seat
        System.out.println(this.client.name() + " (" + Thread.currentThread().getName() + ") is trying to take a seat...");
        this.client.restaurant().takeSeat();
        System.out.println(this.client.name() + " (" + Thread.currentThread().getName() + ") is eating...");

        // Eat for 2 seconds
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Leave the seat
        System.out.println(this.client.name() + " (" + Thread.currentThread().getName() + ") finished eating.");
        this.client.restaurant().leaveSeat();
    }
}
