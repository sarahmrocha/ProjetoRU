package Model;

public class ConfiguracoesAplicacao {

    private static ConfiguracoesAplicacao instancia;

    private String linkBoleto;

    private ConfiguracoesAplicacao() {
    }

    public static ConfiguracoesAplicacao getInstancia() {
        if (instancia == null) {
            instancia = new ConfiguracoesAplicacao();
        }
        return instancia;
    }

    public String getLinkBoleto() {
        return linkBoleto;
    }

    public void setLinkBoleto(String linkBoleto) {
        this.linkBoleto = linkBoleto;
    }
}
