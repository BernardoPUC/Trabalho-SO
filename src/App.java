/**
 * Trabalho SO - Parte 01 (Grupo 02)
 * 
 * Bernardo Aquino Capello Coelho
 * Pedro Luis Gonçalves
 * Kaio Henrique Oliveira da Silveira Barbosa
 * Raquel Inez de Almeida Calazans
 */
public class App {
    public static void main(String[] args) throws Exception {
        Esteira esteira = new Esteira("./dados_tp01.txt");
        BracoMecanico bracoMecanico = new BracoMecanico(esteira);

        bracoMecanico.empacotar();
    }
}
