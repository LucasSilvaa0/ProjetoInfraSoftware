import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

public class Client implements Runnable {
    Barbershop barbearia; // Ponteiro que ligue o cliente à barbearia
    int atendimento; // Sinal para dizer se o cliente está sendo atendido agora ou não
    int numero; // Número do cliente entre as threads

    private static ReentrantLock lock = new ReentrantLock();

    public Client(Barbershop barbearia, int numero) {
        this.barbearia = barbearia;
        this.numero = numero;
        this.atendimento = 0;
    }

    public void ser_atendido() {
        this.atendimento = 1;
    } // Função para iniciar o atendimento do cliente

    public void run() {
        System.out.println("Olá, eu sou o cliente " + this.numero + " e irei agora para a barbearia.");

        try {
            Thread.sleep(500);  // Tempo até a chegada do cliente à barbearia
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }

        int entrada = 0;

        try {
            lock.lock(); // Trancamento da região crítica
            entrada = this.barbearia.entrar(this); // Verificação se tem vaga para um novo cliente na barbearia
        } finally {
            if (Objects.equals(entrada, 0)) {
                System.out.println("[Cliente " + this.numero + "]: Infelizmente a barbearia está lotada, não terei como entrar.");
            } else if (Objects.equals(entrada, 1)) {
                System.out.println("[Cliente " + this.numero + "]: Acabei de chegar na barbearia e já serei atendido agora!");
            } else {
                System.out.println("[Cliente " + this.numero + "]: Acabei de chegar na barbearia e serei o " + (entrada-1) + "º da fila.");
            }
            lock.unlock(); // Abertura da região crítica
        }

         if (Objects.equals(entrada, 1)) { // Caso no qual o cliente chega sendo atendido
            this.atendimento = 1;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }

            System.out.println("[Cliente " + this.numero + "]: Meu atendimento acabou de ser finalizado!");
            this.barbearia.sair();
        } else if (entrada > 1) { // Caso no qual o cliente entra na fila para ser atendido
            while (Objects.equals(this.atendimento, 0)) { // Enquanto o cliente não está sendo atendido, precisa esperar na fila
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException();
                }
            };

            System.out.println("[Cliente " + this.numero + "]: Vou ser atendido agora!");

            try {
                Thread.sleep(100); // Tempo do atendimento ao cliente
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }

            System.out.println("[Cliente " + this.numero + "]: Meu atendimento acabou de ser finalizado!");
            this.barbearia.sair();
        }
    }
}
