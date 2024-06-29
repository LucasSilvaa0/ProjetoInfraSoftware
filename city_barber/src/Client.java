import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

public class Client implements Runnable {
    Barbershop barbearia;
    int atendimento;
    int numero;

    private static ReentrantLock lock = new ReentrantLock();

    public Client(Barbershop barbearia, int numero) {
        this.barbearia = barbearia;
        this.numero = numero;
        this.atendimento = 0;
    }

    public void ser_atendido() {
        this.atendimento = 1;
    }

    public void run() {
        System.out.println("Olá, eu sou o cliente " + this.numero + " e irei agora para a barbearia.");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }

        int entrada = 0;

        try {
            lock.lock();
            entrada = this.barbearia.entrar(this);
        } finally {
            if (Objects.equals(entrada, 0)) {
                System.out.println("[Cliente " + this.numero + "]: Infelizmente a barbearia está lotada, não terei como entrar.");
            } else if (Objects.equals(entrada, 1)) {
                System.out.println("[Cliente " + this.numero + "]: Acabei de chegar na barbearia e já serei atendido agora!");
            } else {
                System.out.println("[Cliente " + this.numero + "]: Acabei de chegar na barbearia e serei o " + (entrada-1) + "º da fila.");
            }
            lock.unlock();
        }

         if (Objects.equals(entrada, 1)) {
            this.atendimento = 1;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }

            System.out.println("[Cliente " + this.numero + "]: Meu atendimento acabou de ser finalizado!");
            this.barbearia.sair();
        } else if (entrada > 1){
            while (Objects.equals(this.atendimento, 0)) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException();
                }
            };

            System.out.println("[Cliente " + this.numero + "]: Vou ser atendido agora!");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }

            System.out.println("[Cliente " + this.numero + "]: Meu atendimento acabou de ser finalizado!");
            this.barbearia.sair();
        }
    }
}
