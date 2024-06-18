import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

public class Prob2Sincronizado {
    private static ReentrantLock lock = new ReentrantLock();

    public static class Semaforo {
        int limite;

        public Semaforo() {
            this.limite = 1;
        }

        public int entrar(int num) {
            if (Objects.equals(this.limite, 1)) {
                this.limite = 0;
                return 1;
            }
            return 0;
        }

        public void sair() {
            this.limite = 1;
        }
    }

    public static class Carro implements Runnable {
        int num;
        String lado;
        Semaforo semaforo;

        public Carro(int num, String lado, Semaforo semaforo) {
            this.num = num;
            this.lado = lado;
            this.semaforo = semaforo;
            if (Objects.equals(this.lado, "D")) {
                System.out.println("[CARRO " + this.num + "]: vou tentar passar da direita para a esquerda!!!");
            } else {
                System.out.println("[CARRO " + this.num + "]: vou tentar passar da esquerda para a direita!!!");
            }

        }

        public void run() {
            int passando = 0;
            while (Objects.equals(passando, 0)) {
                lock.lock();
                try {
                    passando = this.semaforo.entrar(this.num);
                } finally {
                    lock.unlock();
                }
            }

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

            this.semaforo.sair();

        }
    }

    public static void main(String[] args) {
        Semaforo semaforo = new Semaforo();
        Thread[] ti = new Thread[30];

        for (int i = 1; i <= 30; i++) {
            if (i % 2 == 0) {
                Carro car = new Carro(i, "D", semaforo);
                ti[i-1] = new Thread(car);
            } else {
                Carro car = new Carro(i, "E", semaforo);
                ti[i-1] = new Thread(car);
            }
        }

        for (int i = 1; i <= 30; i++) {
            ti[i-1].start();
        }
    }
}