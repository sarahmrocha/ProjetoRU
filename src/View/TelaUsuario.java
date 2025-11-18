package View;

import Controller.ISistemaRU;
import Model.CardapioDiario;
import Model.ItemCardapio;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class TelaUsuario {

    private ISistemaRU controller;
    private ListView<ItemCardapio> listaAlmoco;
    private ListView<ItemCardapio> listaJantar;
    private ChoiceBox<LocalDate> choiceDate;

    public Pane criarTela(ISistemaRU controller) {
        this.controller = controller;

        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(10));

        // --- TOPO: Seleção de Data e Link do Boleto ---
        choiceDate = new ChoiceBox<>();

        // Carrega apenas datas com cardápios publicados
        carregarDatasPublicadas();

        choiceDate.setOnAction(e -> carregarCardapio(choiceDate.getValue()));

        // Obtém o link do boleto do controller
        String linkBoleto = controller.obterLinkBoleto();
        Hyperlink hyperlinkBoleto = new Hyperlink("Gerar Boleto RU");
        if (linkBoleto != null && !linkBoleto.isEmpty()) {
            // (Aqui você adicionaria a lógica para abrir o link no navegador)
            hyperlinkBoleto.setOnAction(e -> System.out.println("Abrindo: " + linkBoleto));
        } else {
            hyperlinkBoleto.setText("Link do boleto não configurado");
            hyperlinkBoleto.setDisable(true);
        }

        HBox topoLayout = new HBox(20, new Label("Ver cardápio de:"), choiceDate, hyperlinkBoleto);
        layout.setTop(topoLayout);

        // --- CENTRO: Listas (Apenas Leitura) ---
        listaAlmoco = new ListView<>();
        listaJantar = new ListView<>();

        // Reusa a célula customizada (se ela for pública ou movida)
        listaAlmoco.setCellFactory(lv -> new TelaConfigCardapio.ItemCell());
        listaJantar.setCellFactory(lv -> new TelaConfigCardapio.ItemCell());

        // Desabilita a edição
        listaAlmoco.setEditable(false);
        listaJantar.setEditable(false);

        VBox almocoBox = new VBox(new Label("Almoço"), listaAlmoco);
        VBox jantarBox = new VBox(new Label("Jantar"), listaJantar);

        HBox centroLayout = new HBox(10, almocoBox, jantarBox);
        layout.setCenter(centroLayout);

        // Carrega o primeiro cardápio disponível, se houver
        if (!choiceDate.getItems().isEmpty()) {
            choiceDate.getSelectionModel().selectFirst();
            carregarCardapio(choiceDate.getValue());
        }

        return layout;
    }

    private void carregarDatasPublicadas() {
        // Pede ao controller todas as datas
        List<LocalDate> datasPublicadas = controller.listarDatasDisponiveis()
                .stream()
                // Filtra para pegar apenas as publicadas
                .filter(data -> controller.obterCardapio(data).isPublicado())
                .collect(Collectors.toList());

        choiceDate.setItems(FXCollections.observableArrayList(datasPublicadas));
    }

    private void carregarCardapio(LocalDate data) {
        if (data == null) return;

        CardapioDiario cardapio = controller.obterCardapio(data);
        // Mostra apenas se estiver publicado (segurança extra)
        if (cardapio.isPublicado()) {
            listaAlmoco.setItems(FXCollections.observableArrayList(cardapio.getItensAlmoco()));
            listaJantar.setItems(FXCollections.observableArrayList(cardapio.getItensJantar()));
        }
    }
}