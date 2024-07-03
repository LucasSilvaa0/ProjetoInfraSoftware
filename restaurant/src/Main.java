public class Main {
    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant();

        // Create 100 clients
        for (int i = 0; i < 100; i++) {
            Client client = new Client("Client " + i, restaurant);
            client.eat();
        }

    }
}