//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Barbershop barbershop = new Barbershop();

        Thread clients[] = new Thread[15]; // Criando espaço de memória para salvar as threads
        for (int i = 1; i <= 15; i++) {
            clients[i-1] = new Thread(new Client(barbershop, i)); // Criando os clientes
        }

        for (int i = 0; i < 15; i++) {
            clients[i].start(); // Dando start em todas as threads criadas

            if (Object.equals(i, 8) || Object.equals(i, 20)) {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    throw new RuntimeException();
                }
            }
        }
    }
}