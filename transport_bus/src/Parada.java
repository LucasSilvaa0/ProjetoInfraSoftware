import java.util.Objects;

public class Parada {
    int onibus_livre;
    int qtd_pessoas;

    public Parada() {
        this.onibus_livre = 0;
        this.qtd_pessoas = 0;
    }

    public void nova_pessoa() { this.qtd_pessoas++; }

    public void saida_pessoa() { this.qtd_pessoas--; }

    public void novo_onibus() {
        this.onibus_livre = 1;
        System.out.println("Um novo ônibus acabou de chegar!!");
    }

    public void saida_onibus() {
        this.onibus_livre = 0;
        if (Objects.equals(this.qtd_pessoas, 0)) {
            System.out.println("O ônibus acabou de ir embora e todos da parada conseguiram pegá-lo!");
        } else {
            System.out.println("O ônibus acabou de ir embora e, infelizmente, ainda sobraram " + this.qtd_pessoas + " esperando pelo próximo.");
        }
    }
}
