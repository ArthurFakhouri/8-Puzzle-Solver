package pkg8puzzle;

public class Nofila {
    private Infofila info;
    private Nofila prox;
    private int prioridade;

    public Nofila(Infofila info, Nofila prox, int prioridade) {
        this.info = info;
        this.prox = prox;
        this.prioridade = prioridade;
    }

    public Infofila getInfo() {
        return info;
    }

    public void setInfo(Infofila info) {
        this.info = info;
    }

    public Nofila getProx() {
        return prox;
    }

    public void setProx(Nofila prox) {
        this.prox = prox;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }
}
