package Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

import Model.CardapioDiario;
import Model.ItemCardapio;
import Model.TipoRefeicao;
import Model.RepositorioCardapio;
import Model.MemoriaRepositorioCardapio;
import Model.ConfiguracoesAplicacao;

/**
 * Controlador principal do sistema de gerenciamento do RU (Restaurante Universitário).*
 * Esta classe implementa a interface ISistemaRU e coordena as operações entre
 * a camada do View e Model de dados, incluindo:
 *  - Gerenciamento de cardápios diários
 *  - Adição, edição, movimentação e remoção de itens do cardápio
 *  - Controle de publicação de cardápios
 *  - Acesso a configuração da aplicação
 */
public class ControleRU implements ISistemaRU {

    /** Repositório para armazenamento e recuperação de cardápios */
    private final RepositorioCardapio repositorio;

    /** Configurações globais da aplicação (Singleton) */
    private final ConfiguracoesAplicacao config;

    /**
     * Contador estático para geração de IDs únicos dos itens do cardápio.*
     * Este atributo é static para garantir que os IDs sejam únicos em toda
     * a aplicação, mesmo que múltiplas instâncias do ControleRU sejam criadas.
     * O valor inicial é 1 e é incrementado automaticamente cada vez que um
     * novo item é adicionado ao cardápio através do método gerarNovoId().
     * Thread-safe: O acesso é sincronizado através do método gerarNovoId().
     */
    private static long proximoId = 1;

    /**
     * Construtor padrão que inicializa o controlador com implementações concretas.
     * Cria uma nova instância de MemoriaRepositorioCardapio e obtém a instância
     * única de ConfiguracoesAplicacao.
     */
    public ControleRU() {
        this.repositorio = new MemoriaRepositorioCardapio();
        this.config = ConfiguracoesAplicacao.getInstancia();
    }

    /**
     * Gera um novo ID único para um item do cardápio.*
     * Este método é sincronizado para garantir thread-safety, evitando que
     * múltiplas threads gerem o mesmo ID simultaneamente.*
     * O ID gerado é sequencial, começando em 1 e incrementando a cada chamada.
     * Uma vez gerado, o ID nunca é reutilizado durante a execução da aplicação.
     *
     * @return o próximo ID único disponível
     */
    private static synchronized long gerarNovoId() {
        return proximoId++;
    }

    /**
     * Lista todas as datas para as quais existem cardápios cadastrados no sistema.
     *
     * @return lista de datas com cardápios disponíveis, ordenadas cronologicamente
     */
    @Override
    public List<LocalDate> listarDatasDisponiveis() {
        List<LocalDate> datas = new ArrayList<>();

        // Itera sobre todos os cardápios e coleta suas datas
        for (CardapioDiario cardapio : repositorio.listar()) {
            datas.add(cardapio.getData());
        }

        // Ordena as datas em ordem cronológica
        datas.sort(LocalDate::compareTo);

        return datas;
    }

    /**
     * Obtém o cardápio completo de uma data específica.
     *
     * @param data data do cardápio desejado
     * @return cardápio da data especificada, ou um cardápio vazio se não existir
     * @throws NullPointerException se data for nula
     */
    @Override
    public CardapioDiario obterCardapio(LocalDate data) {
        Objects.requireNonNull(data, "Data não pode ser nula.");
        return repositorio.obter(data);
    }

    /**
     * Adiciona um novo item ao cardápio de uma data específica.
     * Se o cardápio da data não existir, será criado automaticamente.*
     * Um ID único é gerado automaticamente para o item através do método
     * gerarNovoId(), garantindo que cada item possa ser identificado
     * individualmente nas operações de edição, movimentação e remoção.
     *
     * @param data data do cardápio onde o item será adicionado
     * @param tipo tipo da refeição (ALMOCO ou JANTAR)
     * @param nome nome/descrição do item do cardápio
     * @throws NullPointerException se qualquer parâmetro for nulo
     */
    @Override
    public void adicionarItem(LocalDate data, TipoRefeicao tipo, String nome) {
        Objects.requireNonNull(data, "Data não pode ser nula.");
        Objects.requireNonNull(tipo, "Tipo não pode ser nulo.");
        Objects.requireNonNull(nome, "Nome não pode ser nulo.");

        // Obtém o cardápio da data (cria se não existir)
        CardapioDiario cardapio = repositorio.obter(data);

        // Gera um ID único e cria o novo item
        long novoId = gerarNovoId();
        ItemCardapio novoItem = new ItemCardapio(novoId, tipo, nome);
        cardapio.addItem(novoItem);

        // Persiste as alterações
        repositorio.salvar(cardapio);
    }

    /**
     * Edita o nome de um item existente no cardápio.
     *
     * @param idItem ID único do item a ser editado
     * @param novoNome novo nome para o item
     * @throws IllegalArgumentException se o item não for encontrado
     * @throws NullPointerException se novoNome for nulo
     */
    @Override
    public void editarItem(long idItem, String novoNome) {
        Objects.requireNonNull(novoNome, "Novo nome não pode ser nulo");

        // Busca o item em todos os cardápios
        ItemCardapio item = buscarItemPorId(idItem);

        if (item == null) {
            throw new IllegalArgumentException("Item com ID " + idItem + " não encontrado!");
        }

        // Atualiza o nome do item
        item.setNome(novoNome);

        // Persiste a alteração
        CardapioDiario cardapio = buscarCardapioPorItemId(idItem);
        if (cardapio != null) {
            repositorio.salvar(cardapio);
        }
    }

    /**
     * Move um item de uma refeição para outra (almoço para jantar ou vice-versa).
     *
     * @param idItem ID único do item a ser movido
     * @param novoTipo novo tipo de refeição (ALMOCO ou JANTAR)
     * @throws IllegalArgumentException se o item não for encontrado
     * @throws NullPointerException se novoTipo for nulo
     */
    @Override
    public void moverItem(long idItem, TipoRefeicao novoTipo) {
        Objects.requireNonNull(novoTipo, "Tipo de refeição não pode ser nulo");

        // Busca o item e o cardápio que o contém
        ItemCardapio item = buscarItemPorId(idItem);
        CardapioDiario cardapio = buscarCardapioPorItemId(idItem);

        if (item == null || cardapio == null) {
            throw new IllegalArgumentException("Item com ID " + idItem + " não encontrado!");
        }

        // Remove o item da lista atual
        cardapio.removeItem(idItem);

        // Atualiza o tipo e adiciona na lista correta
        item.setTipo(novoTipo);
        cardapio.addItem(item);

        // Persiste as alterações
        repositorio.salvar(cardapio);
    }

    /**
     * Remove permanentemente um item do cardápio.
     *
     * @param idItem ID único do item a ser removido
     * @throws IllegalArgumentException se o item não for encontrado
     */
    @Override
    public void removerItem(long idItem) {
        // Busca o cardápio que contém o item
        CardapioDiario cardapio = buscarCardapioPorItemId(idItem);

        if (cardapio == null) {
            throw new IllegalArgumentException("Item com ID " + idItem + " não encontrado!");
        }

        // Remove o item
        cardapio.removeItem(idItem);

        // Persiste as alterações
        repositorio.salvar(cardapio);
    }

    /**
     * Define o status de publicação de um cardápio.
     * Apenas cardápios publicados são visíveis para os usuários finais.
     *
     * @param data data do cardápio
     * @param publicado true para publicar, false para despublicar
     * @throws IllegalArgumentException se tentar publicar cardápio vazio
     * @throws NullPointerException se data for nula
     */
    @Override
    public void definirPublicado(LocalDate data, boolean publicado) {
        Objects.requireNonNull(data, "Data não pode ser nula");

        CardapioDiario cardapio = repositorio.obter(data);

        // Verifica se o cardápio tem itens antes de publicar
        if (publicado && cardapio.getItensAlmoco().isEmpty() && cardapio.getItensJantar().isEmpty()) {
            throw new IllegalArgumentException("Não é possível publicar um cardápio vazio!");
        }

        cardapio.setPublicado(publicado);
        repositorio.salvar(cardapio);
    }

    /**
     * Obtém o link do boleto do RU configurado no sistema.
     *
     * @return URL do boleto ou null se não configurado
     */
    @Override
    public String obterLinkBoleto() {
        return config.getLinkBoleto();
    }

    // ==================== Métodos Auxiliares Privados ====================

    /**
     * Busca um item específico percorrendo todos os cardápios cadastrados.
     *
     * @param idItem ID do item procurado
     * @return ItemCardapio encontrado ou null se não existir
     */
    private ItemCardapio buscarItemPorId(long idItem) {
        for (CardapioDiario cardapio : repositorio.listar()) {
            // Busca nos itens do almoço
            for (ItemCardapio item : cardapio.getItensAlmoco()) {
                if (item.getId() == idItem) {
                    return item;
                }
            }
            // Busca nos itens do jantar
            for (ItemCardapio item : cardapio.getItensJantar()) {
                if (item.getId() == idItem) {
                    return item;
                }
            }
        }
        return null;
    }

    /**
     * Busca o cardápio que contém um item específico.
     *
     * @param idItem ID do item procurado
     * @return CardapioDiario que contém o item ou null se não encontrado
     */
    private CardapioDiario buscarCardapioPorItemId(long idItem) {
        for (CardapioDiario cardapio : repositorio.listar()) {
            // Verifica se o item está no almoço
            for (ItemCardapio item : cardapio.getItensAlmoco()) {
                if (item.getId() == idItem) {
                    return cardapio;
                }
            }
            // Verifica se o item está no jantar
            for (ItemCardapio item : cardapio.getItensJantar()) {
                if (item.getId() == idItem) {
                    return cardapio;
                }
            }
        }
        return null;
    }
}