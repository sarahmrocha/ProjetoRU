package Model;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementação de RepositorioCardapio que armazena os cardápios em memória usando um HashMap
 */
public class MemoriaRepositorioCardapio implements RepositorioCardapio {

    private static Map<LocalDate, CardapioDiario> banco = new HashMap<>();

    /**
     * Obtém o cardápio da data informada e se não existir, um novo pe criado e armazenado automaticamente
     * @param data data do cardapio
     * @return cadapio da data
     */
    @Override public CardapioDiario obter (LocalDate data) {
        return banco.computeIfAbsent(data, k -> new CardapioDiario(k)); // Cria o cardápio automaticamente caso não exista
    }

    /**
     * Salva um cardapio no repositório
     * @param cardapio cardápio a ser salvo
     */
    @Override
    public void salvar (CardapioDiario cardapio) {
        banco.put(cardapio.getData(), cardapio);
    }

    /**
     * Retorna todos os cardápios armazenados
     * @return coleção com cardápios
     */
    @Override
    public Collection<CardapioDiario> listar() {
        return banco.values();
    }
}
