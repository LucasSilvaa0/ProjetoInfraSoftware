public class Prob2 {
    public static class Semaforo {
        int limite;

        public Semaforo(int quantidade) {
            this.limite = quantidade;
        }

        public int entrar() {
            if (this.limite > 0) {
                this.limite--;
                return 1;
            } else {
                return 0;
            }
        }

        public void sair() {
            this.limite++;
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
        }

        public void run() {
            if (this.lado == "D") {
                System.out.println("[CARRO " + this.num + "]: vou tentar passar da direita para a esquerda!!!");
            } else {
                System.out.println("[CARRO " + this.num + "]: vou tentar passar da esquerda para a direita!!!");
            }

            while (this.semaforo.entrar() == 0) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    throw new RuntimeException();
                }
            }

            if (this.lado == "D") {
                System.out.println("[CARRO " + this.num + "]: passando para direita!!!");
            } else {
                System.out.println("[CARRO " + this.num + "]: passando para esquerda!!!");
            }

            System.out.println("PASSEI!!!!!!!!!!");

            this.semaforo.sair();

        }
    }

    public static void main(String[] args) {
        Semaforo semaforo = new Semaforo(1);

        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                Carro car = new Carro(i, "D", semaforo);
                Thread ti = new Thread(car);
                ti.start();
            } else {
                Carro car = new Carro(i, "E", semaforo);
                Thread ti = new Thread(car);
                ti.start();
            }
        }
    }
}