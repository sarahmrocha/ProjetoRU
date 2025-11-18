package View;

import Controller.ISistemaRU;
import Model.CardapioDiario;
import Model.ItemCardapio;
import Model.TipoRefeicao;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.Optional;

public class TelaConfigCardapio {

    private ISistemaRU controller;
    private ListView<ItemCardapio> listaAlmoco;
    private ListView<ItemCardapio> listaJantar;
    private DatePicker datePicker;
    private CheckBox checkPublicado;
    private CardapioDiario cardapioAtual;

    public Pane criarTela(ISistemaRU controller) {
        this.controller = controller;

        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(10));

        // --- TOPO: Seleção de Data ---
        datePicker = new DatePicker(LocalDate.now());
        datePicker.setOnAction(e -> atualizarCardapio(datePicker.getValue()));

        checkPublicado = new CheckBox("Publicado");
        checkPublicado.setOnAction(e -> publicarCardapio());

        HBox topoLayout = new HBox(10, new Label("Data:"), datePicker, checkPublicado);
        topoLayout.setAlignment(Pos.CENTER_LEFT);
        layout.setTop(topoLayout);

        // --- CENTRO: Listas de Itens ---
        listaAlmoco = new ListView<>();
        listaJantar = new ListView<>();

        // Usar uma célula customizada para mostrar só o nome do item
        Callback<ListView<ItemCardapio>, ListCell<ItemCardapio>> cellFactory = lv -> new ItemCell();
        listaAlmoco.setCellFactory(cellFactory);
        listaJantar.setCellFactory(cellFactory);

        VBox almocoBox = new VBox(new Label("Almoço"), listaAlmoco);
        VBox jantarBox = new VBox(new Label("Jantar"), listaJantar);

        HBox centroLayout = new HBox(10, almocoBox, jantarBox);
        layout.setCenter(centroLayout);
        BorderPane.setMargin(centroLayout, new Insets(10, 0, 10, 0));

        // --- DIREITA: Botões de Ação ---
        Button btnAdicionarAlmoco = new Button("Add Almoço");
        btnAdicionarAlmoco.setOnAction(e -> adicionarItem(TipoRefeicao.ALMOCO));

        Button btnAdicionarJantar = new Button("Add Jantar");
        btnAdicionarJantar.setOnAction(e -> adicionarItem(TipoRefeicao.JANTAR));

        Button btnRemover = new Button("Remover Item");
        btnRemover.setOnAction(e -> removerItem());

        // TODO: Adicionar botões para Editar e Mover
        // Button btnEditar = new Button("Editar Item");
        // btnEditar.setOnAction(e -> editarItem());
        // Button btnMover = new Button("Mover Item");
        // btnMover.setOnAction(e -> moverItem());

        VBox botoesLayout = new VBox(10, btnAdicionarAlmoco, btnAdicionarJantar, btnRemover);
        botoesLayout.setAlignment(Pos.TOP_CENTER);
        botoesLayout.setPadding(new Insets(20, 0, 0, 10));
        layout.setRight(botoesLayout);

        // --- Carregar dados iniciais ---
        atualizarCardapio(datePicker.getValue());

        return layout;
    }

    /**
     * Busca o cardápio da data selecionada no controlador e atualiza as listas
     */
    private void atualizarCardapio(LocalDate data) {
        if (data == null) return;

        // Usa o controller para obter os dados do Model
        cardapioAtual = controller.obterCardapio(data);

        // Atualiza as listas da UI
        listaAlmoco.setItems(FXCollections.observableArrayList(cardapioAtual.getItensAlmoco()));
        listaJantar.setItems(FXCollections.observableArrayList(cardapioAtual.getItensJantar()));

        // Atualiza o checkbox de publicação
        checkPublicado.setSelected(cardapioAtual.isPublicado());
    }

    /**
     * Pede o nome do novo item e chama o controlador para adicioná-lo
     */
    private void adicionarItem(TipoRefeicao tipo) {
        LocalDate data = datePicker.getValue();
        if (data == null) return;

        TextInputDialog dialog = new TextInputDialog("Novo Prato");
        dialog.setTitle("Adicionar Item");
        dialog.setHeaderText("Digite o nome do prato para " + tipo.name());
        dialog.setContentText("Nome:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(nome -> {
            if (!nome.trim().isEmpty()) {
                // Chama o controlador para salvar o novo item
                controller.adicionarItem(data, tipo, nome);
                // Atualiza a tela para mostrar o item adicionado
                atualizarCardapio(data);
            }
        });
    }

    /**
     * Remove o item selecionado (de qualquer uma das listas)
     */
    private void removerItem() {
        ItemCardapio itemSelecionado = listaAlmoco.getSelectionModel().getSelectedItem();
        if (itemSelecionado == null) {
            itemSelecionado = listaJantar.getSelectionModel().getSelectedItem();
        }

        if (itemSelecionado != null) {
            // Chama o controlador para remover o item
            controller.removerItem(itemSelecionado.getId());
            // Atualiza a tela
            atualizarCardapio(datePicker.getValue());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Nenhum item selecionado!");
            alert.showAndWait();
        }
    }

    /**
     * Chama o controlador para definir o status de publicação do cardápio
     */
    private void publicarCardapio() {
        LocalDate data = datePicker.getValue();
        if (data == null || cardapioAtual == null) return;

        try {
            // Chama o controlador para alterar o status
            controller.definirPublicado(data, checkPublicado.isSelected());
        } catch (IllegalArgumentException e) {
            // O controller lança exceção se tentar publicar vazio
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
            // Desfaz a seleção
            checkPublicado.setSelected(false);
        }
    }


    /**
     * Classe interna para renderizar o ItemCardapio na ListView,
     * mostrando apenas seu nome.
     */
    static class ItemCell extends ListCell<ItemCardapio> {
        @Override
        protected void updateItem(ItemCardapio item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                setText(item.getNome());
            }
        }
    }
}