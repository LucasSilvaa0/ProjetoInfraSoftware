import java.util.Objects;

public class Barbershop {
    public int vagas_ocupadas; // Quantidade de clientes dentro da barbearia
    public int barbeiro_acordado; // Sinal para dizer se o barbeiro está acordado ou não
    Client clients[]; // Ponteiros para cada um dos clientes dentro da barbearia
    int counter_arrived; // Quantidade de clientes que entraram na barbearia até agora no total
    int counter_finished; // Quantidade de clientes que terminaram de ser atendidos até agora no total

    public Barbershop() {
        this.vagas_ocupadas = 0;
        this.barbeiro_acordado = 0;
        this.clients = new Client[5];
        this.counter_arrived = 0;
        this.counter_finished = 0;
        System.out.println("A barbearia foi aberta, mas o barbeiro vai dormir enquanto não chega um cliente.");
    }

    public int entrar(Client client) {
        if (Objects.equals(vagas_ocupadas, 5)) { // Se a barbearia estiver lotada, ninguém pode entrar
            return 0;
        } else if (Objects.equals(vagas_ocupadas, 0)) { // Se a barbearia estiver vazia, o barbeiro precisa acordar para iniciar os atendimentos
            this.vagas_ocupadas++;
            this.clients[counter_arrived%5] = client;
            counter_arrived++;
            this.barbeiro_acordado = 1;
            System.out.println("Eita, parece que o barbeiro precisou acordar.");
            return this.vagas_ocupadas;
        } else { // Se a barbearia já estiver atendendo alguém, o novo cliente irá esperar na fila
            this.vagas_ocupadas++;
            this.clients[counter_arrived%5] = client;
            counter_arrived++;
            return this.vagas_ocupadas;
        }
    }

    public void sair() {
        this.vagas_ocupadas--;
        if (Objects.equals(this.vagas_ocupadas, 0)) { // Se a barbearia ficar vazia, o barbeiro voltará a dormir
            System.out.println("Acabaram os clientes, o barbeiro vai voltar a dormir!");
        } else { // Se a barbearia ainda estiver com clientes, o barbeiro irá atender o próximo
            this.counter_finished++;
            this.clients[this.counter_finished%5].ser_atendido();
        }
    }
}
