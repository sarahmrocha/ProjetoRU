package Model;

/**
 * Representa um item individual do cardápio, como feijão, arroz, macarrão...
 * Cada item possui:
 *  um identificador (id),
 *  um tipo de refeição (almoço ou jantar)
 *  um nome (descrição do prato)
 */
public class ItemCardapio {
    private long id;
    private TipoRefeicao tipo;
    private String nome;

    /**
     * Constrói um novo item do cardápio
     * @param id ID do item
     * @param tipo Tipo da refeição (Almoço ou Jantar)
     * @param nome Nome do prato
     * */
    public ItemCardapio(long id, TipoRefeicao tipo, String nome) {
        this.id = id;
        this.tipo = tipo;
        this.nome = nome;
    }

    /**
     * Getters e Setters
     */

    public long getId() {
        return id;
    }
    public TipoRefeicao getTipo() {
        return tipo;
    }
    public String getNome() {
        return nome;
    }

    public void setTipo(TipoRefeicao tipo) {
        this.tipo = tipo;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
}
