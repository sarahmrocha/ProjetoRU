import Controller.ControleRU;
import Controller.ISistemaRU;
import View.TelaConfigCardapio;
import View.TelaUsuario;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage;
    private ISistemaRU controller; // Controlador ÚNICO compartilhado

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;

        // 1. Inicializa o Controller UMA VEZ
        this.controller = new ControleRU();

        // 2. Configura a janela
        primaryStage.setTitle("Sistema RU - Universidade");
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);

        // 3. Exibe o menu inicial
        exibirMenuPrincipal();

        primaryStage.show();
    }

    // --- TELA 1: MENU PRINCIPAL ---
    private void exibirMenuPrincipal() {
        Label titulo = new Label("Bem-vindo ao RU");
        titulo.setFont(new Font("Arial", 24));

        Button btnSouAluno = new Button("Sou Aluno (Ver Cardápio)");
        btnSouAluno.setStyle("-fx-font-size: 16px; -fx-padding: 10px 20px;");
        btnSouAluno.setOnAction(e -> exibirTelaUsuario());

        Button btnSouAdmin = new Button("Sou Admin (Gerenciar)");
        btnSouAdmin.setStyle("-fx-font-size: 16px; -fx-padding: 10px 20px;");
        btnSouAdmin.setOnAction(e -> exibirTelaAdmin());

        VBox layoutMenu = new VBox(20, titulo, btnSouAluno, btnSouAdmin);
        layoutMenu.setAlignment(Pos.CENTER);

        Scene sceneMenu = new Scene(layoutMenu, 1000, 700);
        primaryStage.setScene(sceneMenu);
    }

    // --- TELA 2: ADMINISTRAÇÃO ---
    private void exibirTelaAdmin() {
        TelaConfigCardapio telaConfig = new TelaConfigCardapio();

        // Precisamos passar uma forma de voltar, se sua TelaConfigCardapio
        // não tiver botão de voltar, você pode adicionar um ou usar a barra do SO.
        // Mas aqui passamos o controller compartilhado:
        Pane painelAdmin = telaConfig.criarTela(controller);

        // Adicionando um botão de voltar "na marra" no topo se precisar,
        // ou apenas trocando a cena:
        VBox container = new VBox();
        Button btnVoltar = new Button("<< Voltar ao Menu");
        btnVoltar.setOnAction(e -> exibirMenuPrincipal());

        container.getChildren().addAll(btnVoltar, painelAdmin);

        primaryStage.setScene(new Scene(container, 1000, 700));
    }

    // --- TELA 3: USUÁRIO/ALUNO ---
    private void exibirTelaUsuario() {
        // Passamos a ação de voltar (exibirMenuPrincipal) para o construtor
        TelaUsuario telaUser = new TelaUsuario(() -> exibirMenuPrincipal());

        Pane painelUser = telaUser.criarTela(controller);

        primaryStage.setScene(new Scene(painelUser, 1000, 700));
    }

    public static void main(String[] args) {
        launch(args);
    }
}