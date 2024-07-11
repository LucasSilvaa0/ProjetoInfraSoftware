import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Onibus extends Thread {
    Semaphore semaforo;
    Parada parada;
    ReentrantLock lock;

    public Onibus(Semaphore semaforo, Parada parada, ReentrantLock lock) {
        this.semaforo = semaforo; // Ponteiro de conexão com o semáforo do ônibus
        this.parada = parada; // Ponteiro de conexão com a parada de ônibus
        this.lock = lock; // Ponteiro de conexão com o lock de Threads
    }

    public void chegar() { // Chegada do ônibus e aumento de limite do semáforo para 50 pessoas entrarem no ônibus
        semaforo.release(50);
        parada.novo_onibus();
    }

    public void run() {
        this.chegar();

        int qtd_antes;

        // Loop em que o ônibus espera a entrada dos passageiros
        // Continuar rodando o loop enquanto ainda possue espaço no ônibus, ou quando passar um tempo sem a entrada de nenhuma pessoa (parada vazia)
        while (semaforo.availablePermits() > 0) {
            qtd_antes = parada.qtd_pessoas;
            try {
                Thread.sleep(200);
                this.lock.lock();
                if (Objects.equals(qtd_antes, parada.qtd_pessoas)) { // Caso onde, após um tempo, nenhum novo passageiro entrou
                    semaforo.acquire(semaforo.availablePermits()); // Limite do semáforo é zerado para a saída do ônibus
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
