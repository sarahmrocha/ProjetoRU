package Model;

public class ItemCardapio {
    private long id;
    private TipoRefeicao tipo;
    private String nome;

    public ItemCardapio(long id, TipoRefeicao tipo, String nome) {
        this.id = id;
        this.tipo = tipo;
        this.nome = nome;
    }

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
