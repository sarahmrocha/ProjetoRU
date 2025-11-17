package Model;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class MemoriaRepositorioCardapio implements RepositorioCardapio {

    private Map<LocalDate, CardapioDiario> banco = new HashMap<>();

    @Override public CardapioDiario obter (LocalDate data) {
        return banco.getOrDefault(data, new CardapioDiario(data));
    }

    @Override
    public void salvar (CardapioDiario cardapio) {
        banco.put(cardapio.getData(), cardapio);
    }
}
