import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Restaurant class that simulates a restaurant with 5 seats.
 * It uses a Semaphore to control the number of seats available and a ReentrantLock to control the access to the seatsTaken variable.
 */
public class Restaurant {
    private final Semaphore seats = new Semaphore(5, true);
    private final ReentrantLock lock = new ReentrantLock();
    private int seatsTaken = 0;

    /**
     * Method to take a seat in the restaurant.
     */
    public void takeSeat() {
        try {
            // Try to acquire a seat
            seats.acquire();
            try {
                // Take the lock to update the seatsTaken variable
                this.lock.lock();
                this.seatsTaken++;
            } finally {
                this.lock.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to leave a seat in the restaurant.
     */
    public void leaveSeat() {
        try {
            // Take the lock to update the seatsTaken variable
            this.lock.lock();
            this.seatsTaken--;

            if (this.seatsTaken == 0) {
                // If this is the last client, release the seats
                System.out.println(Thread.currentThread().getName() + " is the last one. The group is leaving...");
                seats.release(5);
            }
        } finally {
            this.lock.unlock();
        }
    }
}
