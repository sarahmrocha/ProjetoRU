package View;

import Controller.ISistemaRU;
import Model.CardapioDiario;
import Model.ItemCardapio;
import Model.TipoRefeicao;
// Assumindo que você criou o Enum TipoPrato como sugeri
import Model.TipoPrato;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Tela de configuração do cardápio (View do Administrador).
 * Permite selecionar uma data, adicionar, remover e publicar itens
 * do almoço e jantar.
 */
public class TelaConfigCardapio {

    // --- Componentes da UI ---
    private ListView<ItemCardapio> listaAlmoco;
    private ListView<ItemCardapio> listaJantar;
    private DatePicker datePicker;
    private CheckBox checkPublicado;
    private TextField novoPratoField;
    private ComboBox<TipoPrato> tipoPratoComboBox;
    private ComboBox<TipoRefeicao> tipoRefeicaoComboBox;

    // --- Referências de Negócio ---
    private ISistemaRU controller;
    private CardapioDiario cardapioAtual;

    /**
     * Cria e retorna o painel (Pane) principal da tela de configuração.
     *
     * @param controller A instância do controlador do sistema (ISistemaRU) para comunicação.
     * @return Um Pane JavaFX contendo todos os componentes da tela.
     */
    public Pane criarTela(ISistemaRU controller) {
        this.controller = controller;

        // Layout principal com estilo de "card"
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20));
        mainLayout.getStyleClass().add("card-panel");

        // --- TOPO: Controles de Entrada ---
        datePicker = new DatePicker(LocalDate.now());
        datePicker.setOnAction(e -> atualizarCardapio(datePicker.getValue()));
        datePicker.getStyleClass().add("date-picker");

        novoPratoField = new TextField();
        novoPratoField.setPromptText("Nome do prato");
        novoPratoField.getStyleClass().add("text-field");

        // (Requer o Enum TipoPrato)
        tipoPratoComboBox = new ComboBox<>(FXCollections.observableArrayList(TipoPrato.values()));
        tipoPratoComboBox.getSelectionModel().selectFirst();
        tipoPratoComboBox.getStyleClass().add("combo-box");

        tipoRefeicaoComboBox = new ComboBox<>(FXCollections.observableArrayList(TipoRefeicao.values()));
        tipoRefeicaoComboBox.getSelectionModel().selectFirst();
        tipoRefeicaoComboBox.getStyleClass().add("combo-box");

        checkPublicado = new CheckBox("Publicado");
        checkPublicado.setOnAction(e -> publicarCardapio());
        checkPublicado.getStyleClass().add("check-box");

        // Layout do topo
        HBox topControlsLayout = new HBox(10,
                new Label("Data:"), datePicker,
                new Label("Novo Prato:"), novoPratoField,
                new Label("Tipo:"), tipoPratoComboBox,
                tipoRefeicaoComboBox,
                checkPublicado
        );
        topControlsLayout.setAlignment(Pos.CENTER_LEFT);
        mainLayout.setTop(topControlsLayout);
        BorderPane.setMargin(topControlsLayout, new Insets(0, 0, 20, 0));

        // --- CENTRO: Listas de Itens ---
        listaAlmoco = new ListView<>();
        listaAlmoco.getStyleClass().add("list-view");
        listaJantar = new ListView<>();
        listaJantar.getStyleClass().add("list-view");

        // Define como cada célula da lista deve ser renderizada
        Callback<ListView<ItemCardapio>, ListCell<ItemCardapio>> cellFactory = lv -> new ItemCell();
        listaAlmoco.setCellFactory(cellFactory);
        listaJantar.setCellFactory(cellFactory);

        // Labels com estilo
        Label lblAlmoco = new Label("Almoço");
        lblAlmoco.getStyleClass().add("label-heading");
        VBox almocoBox = new VBox(5, lblAlmoco, listaAlmoco);

        Label lblJantar = new Label("Jantar");
        lblJantar.getStyleClass().add("label-heading");
        VBox jantarBox = new VBox(5, lblJantar, listaJantar);

        // Layout do centro
        HBox centerListsLayout = new HBox(20, almocoBox, jantarBox);
        HBox.setHgrow(almocoBox, Priority.ALWAYS); // Faz as listas crescerem
        HBox.setHgrow(jantarBox, Priority.ALWAYS);
        mainLayout.setCenter(centerListsLayout);
        BorderPane.setMargin(centerListsLayout, new Insets(0, 0, 20, 0));

        // --- INFERIOR: Botões de Ação ---
        Button btnAdicionar = new Button("Adicionar");
        btnAdicionar.setOnAction(e -> adicionarItem());
        btnAdicionar.getStyleClass().addAll("button", "button-add");

        Button btnRemover = new Button("Remover");
        btnRemover.setOnAction(e -> removerItem());
        btnRemover.getStyleClass().addAll("button", "button-remove");

        HBox bottomButtonsLayout = new HBox(10, btnAdicionar, btnRemover);
        bottomButtonsLayout.setAlignment(Pos.CENTER_RIGHT);
        mainLayout.setBottom(bottomButtonsLayout);

        // --- Carrega dados iniciais ---
        atualizarCardapio(datePicker.getValue());

        return mainLayout;
    }

    /**
     * Busca o cardápio da data selecionada no controlador e atualiza as listas da UI.
     * @param data A data selecionada no DatePicker.
     */
    private void atualizarCardapio(LocalDate data) {
        if (data == null) return;

        // Usa o controller para obter os dados do Model
        cardapioAtual = controller.obterCardapio(data);

        // Atualiza as listas da UI (ListViews)
        listaAlmoco.setItems(FXCollections.observableArrayList(cardapioAtual.getItensAlmoco()));
        listaJantar.setItems(FXCollections.observableArrayList(cardapioAtual.getItensJantar()));

        // Atualiza o checkbox de publicação
        checkPublicado.setSelected(cardapioAtual.isPublicado());
    }

    /**
     * Pega os dados dos campos de entrada e chama o controlador para adicionar um novo item.
     */
    private void adicionarItem() {
        LocalDate data = datePicker.getValue();
        String nomePrato = novoPratoField.getText();
        TipoPrato tipoPrato = tipoPratoComboBox.getSelectionModel().getSelectedItem();
        TipoRefeicao tipoRefeicao = tipoRefeicaoComboBox.getSelectionModel().getSelectedItem();

        if (data == null || nomePrato == null || nomePrato.trim().isEmpty() || tipoPrato == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Erro", "Preencha todos os campos para adicionar um item.");
            return;
        }

        try {
            // Chama o controlador para salvar o novo item
            // (Requer a mudança no ISistemaRU e ControleRU para aceitar TipoPrato)
            controller.adicionarItem(data, tipoRefeicao, nomePrato, tipoPrato);

            // Atualiza a tela e limpa o campo
            atualizarCardapio(data);
            novoPratoField.clear();
        } catch (IllegalArgumentException e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro ao Adicionar", e.getMessage());
        }
    }

    /**
     * Remove o item selecionado (de qualquer uma das listas).
     */
    private void removerItem() {
        ItemCardapio itemSelecionado = listaAlmoco.getSelectionModel().getSelectedItem();
        if (itemSelecionado == null) {
            itemSelecionado = listaJantar.getSelectionModel().getSelectedItem();
        }

        if (itemSelecionado != null) {
            try {
                // Chama o controlador para remover o item
                controller.removerItem(itemSelecionado.getId());
                // Atualiza a tela
                atualizarCardapio(datePicker.getValue());
            } catch (Exception e) {
                exibirAlerta(Alert.AlertType.ERROR, "Erro ao Remover", e.getMessage());
            }
        } else {
            exibirAlerta(Alert.AlertType.WARNING, "Aviso", "Nenhum item selecionado para remover!");
        }
    }

    /**
     * Chama o controlador para definir o status de publicação do cardápio.
     */
    private void publicarCardapio() {
        LocalDate data = datePicker.getValue();
        if (data == null || cardapioAtual == null) return;

        try {
            // Chama o controlador para alterar o status
            controller.definirPublicado(data, checkPublicado.isSelected());
        } catch (IllegalArgumentException e) {
            // O controller lança exceção se tentar publicar vazio
            exibirAlerta(Alert.AlertType.ERROR, "Erro ao Publicar", e.getMessage());
            // Desfaz a seleção
            checkPublicado.setSelected(false);
        }
    }

    /**
     * Exibe um diálogo de alerta simples.
     * @param tipo Tipo do alerta (ERRO, AVISO, INFORMAÇÃO).
     * @param titulo Título da janela do alerta.
     * @param mensagem A mensagem a ser exibida.
     */
    private void exibirAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }


    /**
     * Classe interna para renderizar o ItemCardapio na ListView.
     * Mostra o nome do prato e seu tipo (ex: "Arroz (Acompanhamento)").
     */
    static class ItemCell extends ListCell<ItemCardapio> {
        @Override
        protected void updateItem(ItemCardapio item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                // (Requer que ItemCardapio tenha o método getTipoPrato())
                setText(item.getNome() + " (" + item.getTipoPrato().name() + ")");
            }
        }
    }
}