public class Main {
    public static void main(String[] args) {
        Bathroom bathroom = new Bathroom();

        // Create 100 people
        for (int i = 0; i < 100; i++) {
            // Randomly choose the gender
            Gender gender = Math.random() >= 0.5 ? Gender.F : Gender.M;
            Person person = new Person("Person " + i, gender, bathroom);
            person.goToBathroom();

            // Randomly wait before creating the next person
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }
}