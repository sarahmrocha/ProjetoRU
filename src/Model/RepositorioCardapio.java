package Model;
import java.time.LocalDate;

public interface RepositorioCardapio {
    CardapioDiario obter (LocalDate data);
    void salvar (CardapioDiario cardapio);
}
