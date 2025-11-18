package View;

import Controller.ISistemaRU;
import Model.CardapioDiario;
import Model.ItemCardapio;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Callback;
import java.time.LocalDate;

public class TelaUsuario {

    private ListView<ItemCardapio> listaAlmoco;
    private ListView<ItemCardapio> listaJantar;
    private DatePicker datePicker;
    private Label lblStatus; // Para mostrar se está "Fechado" ou "Aberto"

    private ISistemaRU controller;
    private Runnable acaoVoltar; // Ação para o botão voltar

    // Construtor recebe a ação de voltar para o Menu
    public TelaUsuario(Runnable acaoVoltar) {
        this.acaoVoltar = acaoVoltar;
    }

    public Pane criarTela(ISistemaRU controller) {
        this.controller = controller;

        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20));

        // --- TOPO ---
        Button btnVoltar = new Button("Voltar ao Menu");
        btnVoltar.setOnAction(e -> acaoVoltar.run());

        datePicker = new DatePicker(LocalDate.now());
        datePicker.setOnAction(e -> pesquisarCardapio());

        HBox topLayout = new HBox(15, btnVoltar, new Label("Consultar Data:"), datePicker);
        topLayout.setAlignment(Pos.CENTER_LEFT);
        mainLayout.setTop(topLayout);
        BorderPane.setMargin(topLayout, new Insets(0,0,20,0));

        // --- CENTRO ---
        listaAlmoco = new ListView<>();
        listaJantar = new ListView<>();

        // Configura as células para mostrar Nome + Tipo
        Callback<ListView<ItemCardapio>, ListCell<ItemCardapio>> cellFactory = lv -> new ListCell<>() {
            @Override protected void updateItem(ItemCardapio item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.getNome() + " (" + item.getTipoPrato() + ")");
            }
        };
        listaAlmoco.setCellFactory(cellFactory);
        listaJantar.setCellFactory(cellFactory);

        VBox boxAlmoco = new VBox(5, new Label("Almoço"), listaAlmoco);
        VBox boxJantar = new VBox(5, new Label("Jantar"), listaJantar);
        HBox.setHgrow(boxAlmoco, Priority.ALWAYS);
        HBox.setHgrow(boxJantar, Priority.ALWAYS);

        HBox centerLayout = new HBox(20, boxAlmoco, boxJantar);
        mainLayout.setCenter(centerLayout);

        // --- BASE (Status) ---
        lblStatus = new Label("");
        lblStatus.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        HBox bottomLayout = new HBox(lblStatus);
        bottomLayout.setAlignment(Pos.CENTER);
        mainLayout.setBottom(bottomLayout);
        BorderPane.setMargin(bottomLayout, new Insets(20,0,0,0));

        // Carrega dados iniciais
        pesquisarCardapio();

        return mainLayout;
    }

    private void pesquisarCardapio() {
        LocalDate data = datePicker.getValue();
        if (data == null) return;

        listaAlmoco.getItems().clear();
        listaJantar.getItems().clear();

        CardapioDiario cardapio = controller.obterCardapio(data);

        // LÓGICA: Só mostra se estiver publicado
        if (cardapio.isPublicado()) {
            listaAlmoco.setItems(FXCollections.observableArrayList(cardapio.getItensAlmoco()));
            listaJantar.setItems(FXCollections.observableArrayList(cardapio.getItensJantar()));

            if (cardapio.getItensAlmoco().isEmpty() && cardapio.getItensJantar().isEmpty()) {
                lblStatus.setText("Cardápio publicado, mas sem itens cadastrados.");
                lblStatus.setStyle("-fx-text-fill: orange;");
            } else {
                lblStatus.setText("Cardápio Disponível");
                lblStatus.setStyle("-fx-text-fill: green;");
            }
        } else {
            lblStatus.setText("Cardápio não divulgado para esta data.");
            lblStatus.setStyle("-fx-text-fill: red;");
        }
    }
}