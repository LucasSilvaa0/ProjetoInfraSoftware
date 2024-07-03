/**
 * Client class represents a client that can eat in a restaurant.
 *
 * @param name       The name of the client
 * @param restaurant The restaurant where the client can eat
 */
public record Client(String name, Restaurant restaurant) {

    /**
     * Method to make the client eat.
     */
    public void eat() {
        EatTask eatTask = new EatTask(this);
        Thread thread = new Thread(eatTask);
        thread.start();
    }
}

