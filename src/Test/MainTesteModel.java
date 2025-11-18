package Test;

import Model.*;
import java.time.LocalDate;

public class MainTesteModel {
    public static void main(String[] args) {

        // cria o repositório
        RepositorioCardapio repo = new MemoriaRepositorioCardapio();

        // data de hoje
        LocalDate hoje = LocalDate.now();

        // cria cardápio
        CardapioDiario cardapio = new CardapioDiario(hoje);

        // adiciona itens
        cardapio.addItem(new ItemCardapio(1, TipoRefeicao.ALMOCO, "Arroz"));
        cardapio.addItem(new ItemCardapio(2, TipoRefeicao.ALMOCO, "Feijão"));
        cardapio.addItem(new ItemCardapio(3, TipoRefeicao.JANTAR, "Macarrão"));

        // salva
        repo.salvar(cardapio);

        // obtém novamente
        CardapioDiario c = repo.obter(hoje);

        // imprime para testar
        System.out.println("====== CARDÁPIO ======");
        System.out.println("Data: " + c.getData());
        System.out.println("Publicado? " + c.isPublicado());

        System.out.println("\n--- ALMOÇO ---");
        c.getItensAlmoco().forEach(it -> System.out.println(it.getId() + " - " + it.getNome()));

        System.out.println("\n--- JANTAR ---");
        c.getItensJantar().forEach(it -> System.out.println(it.getId() + " - " + it.getNome()));

        // testando remoção
        c.removeItem(2);
        System.out.println("\n--- ALMOÇO (depois de remover) ---");
        c.getItensAlmoco().forEach(it -> System.out.println(it.getId() + " - " + it.getNome()));
    }
}
