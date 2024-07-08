import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Onibus extends Thread {
    Semaphore semaforo;
    Parada parada;
    ReentrantLock lock;

    public Onibus(Semaphore semaforo, Parada parada, ReentrantLock lock) {
        this.semaforo = semaforo;
        this.parada = parada;
        this.lock = lock;
    }

    public void chegar() {
        semaforo.release(50);
        parada.novo_onibus();
    }

    public void run() {
        this.chegar();

        int qtd_antes;
        while (semaforo.availablePermits() > 0) {
            qtd_antes = parada.qtd_pessoas;
            try {
                Thread.sleep(200);
                this.lock.lock();
                if (Objects.equals(qtd_antes, parada.qtd_pessoas)) {
                    semaforo.acquire(semaforo.availablePermits());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                this.lock.unlock();
            }
        }

        parada.saida_onibus();
    }
}
