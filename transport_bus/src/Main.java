import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static class CriarOnibus extends Thread {
        Semaphore semaforo;
        Parada parada;
        ReentrantLock lock;

        public CriarOnibus(Semaphore semaforo, Parada parada, ReentrantLock lock) {
            this.semaforo = semaforo; // Conectar os ônibus ao semáforo
            this.parada = parada; // Conectar os ônibus à parada
            this.lock = lock; // Conectar os ônibus ao lock
        }

        public void run() {
            Random gerador = new Random(); // Gerador de números aleatórios

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            while (parada.qtd_pessoas > 0) {
                if (Objects.equals(parada.onibus_livre, 0)) {
                    Thread onibus = new Thread(new Onibus(this.semaforo, this.parada, this.lock)); // Criação dos ônibus
                    onibus.start();
                }
                try {
                    Thread.sleep(1000*(gerador.nextInt(2)+1)); // Sortear um número entre 1s e 3s para saber o tempo da demora para passagem do próximo ônibus
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static class CriarPessoas extends Thread {
        Semaphore semaforo; // Conectar as pessoas ao semáforo
        Parada parada; // Conectar as pessoas à parada
        ReentrantLock lock; // Conectar as pessoas ao lock

        public CriarPessoas(Semaphore semaforo, Parada parada, ReentrantLock lock) {
            this.semaforo = semaforo;
            this.parada = parada;
            this.lock = lock;
        }

        public void run() {
            for (int i = 0; i < 120; i++) {
                Thread pessoa = new Thread(new Pessoa(i+1, this.semaforo, this.parada, this.lock)); // Criação das Threads das pessoas
                pessoa.start();
            }
        }
    }

    public static void main(String[] args) {
        Semaphore semaforo = new Semaphore(50); // Criação do semáforo com limite de 50 pessoas para cada ônibus que chegar
        semaforo.drainPermits(); // Zerando o semáforo para 0 posições enquanto não há ônibus

        Parada parada = new Parada(); // Criação da parada de ônibus
        ReentrantLock lock = new ReentrantLock(); // Criação do lock de Threads

        Thread criar_pessoas = new Thread(new CriarPessoas(semaforo, parada, lock));
        criar_pessoas.start();

        Thread criar_onibus = new Thread(new CriarOnibus(semaforo, parada, lock));
        criar_onibus.start();
    }
}