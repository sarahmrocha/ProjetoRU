import Controller.ControleRU;
import Controller.ISistemaRU;
import View.TelaConfigCardapio;
// Imports da TelaUsuario e TabPane foram removidos
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

/**
 * Classe principal da aplicação JavaFX.
 * (Versão Simplificada)
 * * Responsável por inicializar o sistema (Controller) e carregar
 * a tela principal de configuração do cardápio (TelaConfigCardapio).
 */
public class Main extends Application {

    /**
     * Ponto de entrada principal para a aplicação JavaFX.
     * Este método é chamado pelo runtime do JavaFX após o launch().
     *
     * @param primaryStage O "palco" principal (janela) da aplicação.
     */
    @Override
    public void start(Stage primaryStage) {
        // 1. Inicializa o Controlador (backend)
        ISistemaRU controller = new ControleRU();

        // 2. Cria a instância da Tela de Configuração
        TelaConfigCardapio telaConfig = new TelaConfigCardapio();

        // 3. Cria o painel (layout) da tela
        Pane painelAdmin = telaConfig.criarTela(controller);

        // 4. Cria a Cena principal diretamente com o painel de admin
        // (Removemos o TabPane)
        Scene scene = new Scene(painelAdmin, 1000, 700);

        // 5. Configura e exibe a Janela (Stage)
        primaryStage.setTitle("Gerenciamento do Cardápio RU");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Método main padrão que lança a aplicação JavaFX.
     *
     * @param args Argumentos de linha de comando (não utilizados aqui).
     */
    public static void main(String[] args) {
        launch(args);
    }
>>>>>>> main
}