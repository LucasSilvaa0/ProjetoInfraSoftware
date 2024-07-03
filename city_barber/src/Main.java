//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Barbershop barbershop = new Barbershop();

        Thread clients[] = new Thread[100]; // Criando espaço de memória para salvar as threads
        for (int i = 1; i <= 100; i++) {
            clients[i-1] = new Thread(new Client(barbershop, i)); // Criando os clientes
        }

        for (int i = 0; i < 100; i++) {
            clients[i].start(); // Dando start em todas as threads criadas
        }
    }
}