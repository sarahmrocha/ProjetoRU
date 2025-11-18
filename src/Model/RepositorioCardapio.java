package Model;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Interface do padrão Repository
 * Define as operações de armazenamento e acesso aos cardápios do RU
 */

public interface RepositorioCardapio {
    /**
     * Retorna o cardápio da data informada e se não existir, uma implementação pode criar um novo
     * @param data data desejada
     * @return cardapio da data
     */
    CardapioDiario obter (LocalDate data);

    /**
     * Salva ou atualiza o cardápio fornecido
     * @param cardapio cardápio a ser salvo
     */
    void salvar (CardapioDiario cardapio);

    /**
     * Devolve todos os cardápios que estão armazenados no repositório
     * @return coleção de cardápios
     */
    Collection<CardapioDiario> listar();
}
