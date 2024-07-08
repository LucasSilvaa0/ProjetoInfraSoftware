import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Pessoa extends Thread {
    int numero;
    Parada parada;
    Semaphore semaforo;
    int entrou;
    ReentrantLock lock;

    public Pessoa(int num, Semaphore semaforo, Parada parada, ReentrantLock lock) {
        this.numero = num;
        this.semaforo = semaforo;
        this.parada = parada;
        this.entrou = 0;
        this.lock = lock;
    }

    public void run() {
        System.out.println("Olá, eu sou a pessoa " + this.numero + " e irei agora para a parada de ônibus.");

        Random gerador = new Random();

        try {
            Thread.sleep(gerador.nextInt(5000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        try {
            this.lock.lock();
            parada.nova_pessoa();
        } finally {
            System.out.println("[Pessoa " + this.numero + "]: Cheguei na parada de ônibus.");
            this.lock.unlock();
        }

        while (this.entrou != 1) {
            try {
                semaforo.acquire(1);
                this.lock.lock();
                parada.saida_pessoa();
                this.entrou = 1;
                System.out.println("[Pessoa " + this.numero + "]: Entrei no ônibus!");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                this.lock.unlock();
            }
        }
    }
}
