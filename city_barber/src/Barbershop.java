import java.util.Objects;

public class Barbershop {
    public int vagas_ocupadas;
    public int barbeiro_acordado;
    Client clients[];
    int counter_arrived;
    int counter_finished;

    public Barbershop() {
        this.vagas_ocupadas = 0;
        this.barbeiro_acordado = 0;
        this.clients = new Client[5];
        this.counter_arrived = 0;
        this.counter_finished = 0;
        System.out.println("A barbearia foi aberta, mas o barbeiro vai dormir enquanto não chega um cliente.");
    }

    public int entrar(Client client) {
        if (Objects.equals(vagas_ocupadas, 5)) {
            return 0;
        } else if (Objects.equals(vagas_ocupadas, 0)) {
            this.vagas_ocupadas++;
            this.clients[counter_arrived%5] = client;
            counter_arrived++;
            this.barbeiro_acordado = 1;
            System.out.println("Eita, parece que o barbeiro precisou acordar.");
            return this.vagas_ocupadas;
        } else {
            this.vagas_ocupadas++;
            this.clients[counter_arrived%5] = client;
            counter_arrived++;
            return this.vagas_ocupadas;
        }
    }

    public void sair() {
        this.vagas_ocupadas--;
        if (Objects.equals(this.vagas_ocupadas, 0)) {
            System.out.println("Acabaram os clientes, o barbeiro vai voltar a dormir!");
        } else {
            this.counter_finished++;
            this.clients[this.counter_finished%5].ser_atendido();
        }
    }
}
