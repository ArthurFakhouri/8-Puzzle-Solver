package pkg8puzzle;

public class Infopilha {
    private No info;
    private Fila fila;

    public Infopilha(No info, Fila fila) {
        this.info = info;
        this.fila = fila;
    }

    public No getInfo() {
        return info;
    }

    public void setInfo(No info) {
        this.info = info;
    }

    public Fila getFila() {
        return fila;
    }

    public void setFila(Fila fila) {
        this.fila = fila;
    }
}
