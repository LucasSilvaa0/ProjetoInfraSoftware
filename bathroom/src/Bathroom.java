import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Bathroom class that represents a bathroom with 3 stalls.
 */
public class Bathroom {
    private final Semaphore stallSemaphore = new Semaphore(3, true);
    private final ReentrantLock lock = new ReentrantLock();
    private final ReentrantLock genderLock = new ReentrantLock();
    private Gender currentGender = null;

    /**
     * Method that simulates a person using the bathroom.
     *
     * @param gender the gender of the person
     * @param name   the name of the person
     * @throws InterruptedException if the thread is interrupted
     */
    public void useBathroom(Gender gender, String name) throws InterruptedException {
        try {
            this.lock.lock(); // Use lock to guarantee that only one person is checking the bathroom at a time
            if (this.currentGender == null) {
                this.currentGender = gender; // If the bathroom is empty, the person can use it
            } else {
                this.genderLock.lock(); // Use genderLock to guarantee that only one person is checking the gender at a time

                if (this.currentGender != gender) { // If the bathroom is not empty and the person is of the opposite
                    System.out.println(name + " (" + gender + ") need to wait. There's someone of the opposite gender using it");

                    this.stallSemaphore.acquire(3); // Wait for the bathroom to be empty, acquiring all permits
                    System.out.println("Now the bathroom is empty. " + name + " (" + gender + ") can use it");
                    this.currentGender = gender; // Change the current gender of the bathroom

                    this.stallSemaphore.release(3); // Release all permits
                } else if (this.genderLock.isHeldByCurrentThread()) { // Else, if the person is of the same gender and is locking
                    this.genderLock.unlock(); // Unlock the genderLock
                }
            }

            this.stallSemaphore.acquire(); // Tries to enter the bathroom, acquiring one permit
            System.out.println(name + " (" + gender + ") started using the bathroom along with " + (3 - this.stallSemaphore.availablePermits() - 1) + " other(s)");
        } catch (InterruptedException e) {
            this.stallSemaphore.release(); // Release the permit, leaving the bathroom if an exception occurs
            throw e;
        } finally { // Always unlock the locks
            if (this.lock.isHeldByCurrentThread()) {
                this.lock.unlock();
            }
            if (this.genderLock.isHeldByCurrentThread()) {
                this.genderLock.unlock();
            }
        }

        Thread.sleep((long) (Math.random() * 10000)); // Simulate the time the person is using the bathroom

        System.out.println(name + " (" + gender + ") finished using the bathroom");
        this.stallSemaphore.release(); // Release the permit, leaving the bathroom
    }
}
