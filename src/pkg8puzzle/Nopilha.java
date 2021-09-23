package pkg8puzzle;

public class Nopilha {
    private Infopilha info;
    private Nopilha prox;
    
    public Nopilha() {}

    public Nopilha(Infopilha info, Nopilha prox) {
        this.info = info;
        this.prox = prox;
    }

    public Infopilha getInfo() {
        return info;
    }

    public void setInfo(Infopilha info) {
        this.info = info;
    }

    public Nopilha getProx() {
        return prox;
    }

    public void setProx(Nopilha prox) {
        this.prox = prox;
    }
}
