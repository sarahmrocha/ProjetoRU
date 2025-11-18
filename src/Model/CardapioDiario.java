package Model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa o cardápio de um dia específico do RU
 * Cada cardápio possui:
 * - uma data
 * - status de publicação
 * - lista de itens para o Almoço
 * - lista de itens para o Jantar
 */
public class CardapioDiario {
    private LocalDate data; // data do cardápio
    private boolean publicado; // indica se o cardapio está disponível para o usuário

    private List<ItemCardapio> itensAlmoco = new ArrayList<>(); // itens da refeição almoço
    private List<ItemCardapio> itensJantar = new ArrayList<>(); // itens da refeição almoço

    /**
     * Construtor para criar um cardápio vazio para uma data
     * As listas são inicializadas aqui para evitar NullPointerException
     * @param data data do cardápio
     */
    public CardapioDiario (LocalDate data) {
        this.data = data;
        this.publicado = false;
        this.itensAlmoco = new ArrayList<>();
        this.itensJantar = new ArrayList<>();
    }

    public LocalDate getData() {
        return data;
    }
    public boolean isPublicado() {
        return publicado;
    }
    public void setPublicado (boolean publicado) {
        this.publicado = publicado;
    }

    public List <ItemCardapio> getItensAlmoco() {
        return itensAlmoco;
    }
    public List <ItemCardapio> getItensJantar() {
        return itensJantar;
    }

    /**
     * Adiciona um item ao cardápio, colocando-o automaticamente na lista certa de acordo com o tipo de refeição
     * @param item item a ser adicionado
     */

    public void addItem (ItemCardapio item) {
        if (item.getTipo() == TipoRefeicao.ALMOCO) {
            itensAlmoco.add(item);
        } else {
            itensJantar.add(item);
        }
    }

    public void removeItem (long id) {
        itensAlmoco.removeIf(it -> it.getId() == id);
        itensJantar.removeIf(it -> it.getId() == id);
    }
}
