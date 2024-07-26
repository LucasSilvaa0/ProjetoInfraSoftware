/**
 * Person class represents a person who can go to the bathroom.
 */
public class Person {
    String name;
    Gender gender;
    Bathroom bathroom;

    /**
     * Represents a person who can go to the bathroom.
     *
     * @param name     person's name
     * @param gender   person's gender
     * @param bathroom bathroom to be used
     */
    public Person(String name, Gender gender, Bathroom bathroom) {
        this.name = name;
        this.gender = gender;
        this.bathroom = bathroom;
    }

    /**
     * Method that simulates a person going to the bathroom.
     */
    public void goToBathroom() {
        new Thread(new UseBathroomTask(this)).start();
    }

    /**
     * Task that simulates a person going to the bathroom.
     */
    private record UseBathroomTask(Person person) implements Runnable {

        /**
         * Method that simulates a person going to the bathroom.
         */
        @Override
        public void run() {
            System.out.println(this.person.name + " (" + this.person.gender + ") is going to bathroom");
            try {
                this.person.bathroom.useBathroom(this.person.gender, this.person.name);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
                System.out.println(this.person.name + " could not use bathroom: " + e.getMessage());
            }
        }
    }
}

