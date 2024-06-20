import java.util.Objects;

public class Prob2Dessincronizado {
    public static class Carro implements Runnable {
        int num;
        String lado;

        public Carro(int num, String lado) {
            this.num = num;
            this.lado = lado;
            if (Objects.equals(this.lado, "D")) {
                System.out.println("[CARRO " + this.num + "]: vou tentar passar da direita para a esquerda!!!");
            } else {
                System.out.println("[CARRO " + this.num + "]: vou tentar passar da esquerda para a direita!!!");
            }

        }

        public void run() {
            if (Objects.equals(this.lado, "D")) {
                System.out.println("[CARRO " + this.num + "]: passando para direita!!!");
            } else {
                System.out.println("[CARRO " + this.num + "]: passando para esquerda!!!");
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }

            System.out.println("Carro " + this.num + " passou!!!!!!!!!!");

        }
    }

    public static void main(String[] args) {
        Thread[] ti = new Thread[30];

        for (int i = 1; i <= 30; i++) {
            if (i % 2 == 0) {
                Carro car = new Carro(i, "D");
                ti[i-1] = new Thread(car);
            } else {
                Carro car = new Carro(i, "E");
                ti[i-1] = new Thread(car);
            }
        }

        for (int i = 1; i <= 30; i++) {
            ti[i-1].start();
        }
    }
}