package Model;

/**
 * Responsável por armazenar configurações globais do sistema, como o link do boleto do RU
 * Implementa o padrão Singleton, o que garante que exista só uma instância dessa classe em toda a aplicação
 */

public class ConfiguracoesAplicacao {

    /**
     * Instancia única da classe (Singleton)
     * É estática para ser compartilhada por toda a aplicação
     */
    private static ConfiguracoesAplicacao instancia;

    /**
     * Link do boleto utilizado na tela do usuário
     */
    private String linkBoleto;

    /**
     * Contrutor privado para impedir que outras classes criem novas instâncias de ConfiguracoesAplicacao
     * O acesso deve ser feito pelo método getInstacia()
     */
    private ConfiguracoesAplicacao() {
    }

    /**
     * Retorna a instância única de ConfiguracoesAplicacao, e se ainda não existir, cria uma nova
     * @return instância única de ConfiguracoesAplicacao
     */
    public static ConfiguracoesAplicacao getInstancia() {
        if (instancia == null) {
            instancia = new ConfiguracoesAplicacao();
        }
        return instancia;
    }

    /**
     * Obtém o link do boleto já configurado
     * @return link do boleto do RU
     */
    public String getLinkBoleto() {
        return linkBoleto;
    }

    /**
     * Define o link do boleto que será exibido para o usuário
     * @param linkBoleto novo link do boleto
     */
    public void setLinkBoleto(String linkBoleto) {
        this.linkBoleto = linkBoleto;
    }
}
