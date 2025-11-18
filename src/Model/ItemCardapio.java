package Model;

/**
 * Representa um item individual do cardápio (ex: Arroz, Feijão).
 * (Originalmente tinha id, tipo, nome)
 *
 * MODIFICADO para incluir TipoPrato e ajustar o construtor.
 * O 'id' agora é gerenciado pelo ControleRU e o 'tipo' (almoco/jantar)
 * é gerenciado pelo CardapioDiario (em qual lista o item está).
 */
public class ItemCardapio {
    private long id;
    private TipoRefeicao tipo; // Mantido para referência
    private String nome;

    /** NOVO CAMPO para o design da UI */
    private TipoPrato tipoPrato;

    /**
     * Construtor atualizado para incluir o TipoPrato.
     * @param id ID único do item (gerado pelo ControleRU)
     * @param tipo Tipo da refeição (Almoço ou Jantar)
     * @param nome Nome do prato
     * @param tipoPrato Categoria do prato (ex: Principal, Salada)
     */
    public ItemCardapio(long id, TipoRefeicao tipo, String nome, TipoPrato tipoPrato) {
        this.id = id;
        this.tipo = tipo;
        this.nome = nome;
        this.tipoPrato = tipoPrato; // Novo
    }

    // Getters existentes
    public long getId() { return id; }
    public TipoRefeicao getTipo() { return tipo; }
    public String getNome() { return nome; }

    /** NOVO GETTER */
    public TipoPrato getTipoPrato() { return tipoPrato; }

    // Setters existentes
    public void setTipo(TipoRefeicao tipo) { this.tipo = tipo; }
    public void setNome(String nome) { this.nome = nome; }

    /** NOVO SETTER */
    public void setTipoPrato(TipoPrato tipoPrato) { this.tipoPrato = tipoPrato; }
}