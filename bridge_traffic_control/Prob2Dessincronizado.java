import java.util.Objects;

public class Prob2Dessincronizado {
    public static class Ponte {
        int limite;

        public Ponte(int limite) {
            this.limite = limite;
        }

        public void passar() {
            this.limite--;
        }
    }

    public static class Carro implements Runnable {
        int num;
        String lado;
        Ponte ponte;

        public Carro(int num, String lado, Ponte ponte) {
            this.num = num;
            this.lado = lado;
            this.ponte = ponte;
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
            this.ponte.passar();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }

            if (this.ponte.limite > 0) {
                System.out.println("Carro " + this.num + " passou!!!!!!!!!!");
            } else {
                System.out.println("Carro " + this.num + " não conseguiu passar porque ficou preso na ponte com outro carro...");
            }

        }
    }

    public static void main(String[] args) {
        Ponte ponte = new Ponte(1);
        Thread[] ti = new Thread[30];

        for (int i = 1; i <= 30; i++) {
            if (i % 2 == 0) {
                Carro car = new Carro(i, "D", ponte);
                ti[i-1] = new Thread(car);
            } else {
                Carro car = new Carro(i, "E", ponte);
                ti[i-1] = new Thread(car);
            }
        }

        for (int i = 1; i <= 30; i++) {
            ti[i-1].start();
        }
    }
}