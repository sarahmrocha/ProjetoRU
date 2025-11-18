package Controller;

import java.time.LocalDate;
import java.util.List;

import Model.CardapioDiario;
import Model.TipoPrato;
import Model.TipoRefeicao;

public interface ISistemaRU {

    List<LocalDate> listarDatasDisponiveis();

    CardapioDiario obterCardapio(LocalDate data);

    void adicionarItem(LocalDate data, TipoRefeicao tipo, String nome, TipoPrato tipoPrato);

    void editarItem(long idItem, String novoNome);

    void moverItem(long idItem, TipoRefeicao novoTipo);

    void removerItem(long idItem);

    void definirPublicado(LocalDate data, boolean publicado);

    String obterLinkBoleto();

}
