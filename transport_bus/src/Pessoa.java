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
        this.numero = num; // Número que representa a pessoa
        this.semaforo = semaforo; // Ponteiro para conexão com o semáforo
        this.parada = parada; // Ponteiro para conexão com a parada
        this.entrou = 0; // Valor que indica se uma pessoa já entrou ou não no ônibus
        this.lock = lock; // Ponteiro para conexão com o lock de Threads
    }

    public void run() {
        System.out.println("Olá, eu sou a pessoa " + this.numero + " e irei agora para a parada de ônibus.");

        Random gerador = new Random(); // Gerador de números aleatórios

        try {
            Thread.sleep(gerador.nextInt(5000)); // Tempo de chegada à parada (entre 0 e 5 segundos)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        try {
            this.lock.lock();
            parada.nova_pessoa(); // Indicar que essa nova pessoa chegou à parada
        } finally {
            if (Objects.equals(parada.onibus_livre, 0)) { // Caso no qual a parada está sem ônibus
                System.out.println("[Pessoa " + this.numero + "]: Cheguei na parada de ônibus.");
                this.lock.unlock();
            } else { // Caso no qual a parada está com ônibus
                System.out.println("[Pessoa " + this.numero + "]: Cheguei na parada de ônibus, mas só poderei entrar no próximo.");
                this.lock.unlock();
                while (Objects.equals(parada.onibus_livre, 1)) { // Esperar o ônibus sair para ser liberado para o próximo
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        while (this.entrou != 1) { // Rodar o loop enquanto a pessoa não entrou em um ônibus
            try { // Tentar entrar em um ônibus (só vai ocorrer caso tenha algum ônibus presente e o semáforo ainda tenha limite > 0)
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
