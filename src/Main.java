import Controller.ControleRU;
import Controller.ISistemaRU;
import View.TelaConfigCardapio;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 1. Inicializa o backend (Controlador)
        ISistemaRU controller = new ControleRU();

        // 2. Inicializa a View (Tela de Configuração)
        TelaConfigCardapio telaConfig = new TelaConfigCardapio();

        // 3. Cria a cena principal passando o controlador para a tela
        // A tela de configuração será responsável por criar seu próprio layout
        Scene scene = new Scene(telaConfig.criarTela(controller), 800, 600);

        // 4. Configura e exibe o "palco" (a janela principal)
        primaryStage.setTitle("Gerenciamento do Cardápio RU");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // O launch(args) é o método que inicia a aplicação JavaFX
        launch(args);
    }
}