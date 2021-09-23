package pkg8puzzle;

public class Infofila {
    private No info;
    private int custo;
    private Pilha pilha;

    public Infofila(No info, int custo, Pilha pilha) {
        this.info = info;
        this.custo = custo;
        this.pilha = pilha;
    }
    
    public No getInfo() {
        return info;
    }

    public void setInfo(No info) {
        this.info = info;
    }

    public int getCusto() {
        return custo;
    }

    public void setCusto(int custo) {
        this.custo = custo;
    }

    public Pilha getPilha() {
        return pilha;
    }

    public void setPilha(Pilha pilha) {
        this.pilha = pilha;
    }
}
