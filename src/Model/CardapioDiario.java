package Model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CardapioDiario {
    private LocalDate data;
    private boolean publicado;

    private List<ItemCardapio> itensAlmoco = new ArrayList<>();
    private List<ItemCardapio> itensJantar = new ArrayList<>();

    public CardapioDiario (LocalDate data) {
        this.data = data;
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
