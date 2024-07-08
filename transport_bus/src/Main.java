import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static class CriarOnibus extends Thread {
        Semaphore semaforo;
        Parada parada;
        ReentrantLock lock;

        public CriarOnibus(Semaphore semaforo, Parada parada, ReentrantLock lock) {
            this.semaforo = semaforo;
            this.parada = parada;
            this.lock = lock;
        }

        public void run() {
            Random gerador = new Random();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            while (parada.qtd_pessoas > 0) {
                Thread onibus = new Thread(new Onibus(this.semaforo, this.parada, this.lock));
                onibus.start();
                try {
                    Thread.sleep(1000*(gerador.nextInt(2)+1));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static class CriarPessoas extends Thread {
        Semaphore semaforo;
        Parada parada;
        ReentrantLock lock;

        public CriarPessoas(Semaphore semaforo, Parada parada, ReentrantLock lock) {
            this.semaforo = semaforo;
            this.parada = parada;
            this.lock = lock;
        }

        public void run() {
            for (int i = 0; i < 120; i++) {
                Thread pessoa = new Thread(new Pessoa(i+1, this.semaforo, this.parada, this.lock));
                pessoa.start();
            }
        }
    }

    public static void main(String[] args) {
        Semaphore semaforo = new Semaphore(50);
        semaforo.drainPermits();

        Parada parada = new Parada();
        ReentrantLock lock = new ReentrantLock();

        Thread criar_pessoas = new Thread(new CriarPessoas(semaforo, parada, lock));
        criar_pessoas.start();

        Thread criar_onibus = new Thread(new CriarOnibus(semaforo, parada, lock));
        criar_onibus.start();
    }
}