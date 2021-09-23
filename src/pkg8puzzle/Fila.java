package pkg8puzzle;

public class Fila {
    
    private Nofila fila;
    
    public void enqueue(No info, int prioridade){
        this.enqueue(info, 0, null, prioridade);
    }
    
    public void enqueue(No info, int custo, Nopilha p, int prioridade) {
        Nofila novo = new Nofila(new Infofila(info, custo, new Pilha(p)), null, prioridade);
        if (fila == null) {
            fila = novo;
        } else {
            Nofila aux = fila, ant = null;
            while (aux.getProx() != null && novo.getPrioridade() >= aux.getPrioridade()) {
                ant = aux;
                aux = aux.getProx();
            }
            if (aux == fila) {
                if (novo.getPrioridade() >= aux.getPrioridade()) {
                    aux.setProx(novo);
                } else {
                    novo.setProx(aux);
                    fila = novo;
                }
            } else {
                if (aux.getProx() == null) {
                    if (novo.getPrioridade() >= aux.getPrioridade()) {
                        aux.setProx(novo);
                    } else {
                        ant.setProx(novo);
                        novo.setProx(aux);
                    }
                } else {
                    ant.setProx(novo);
                    novo.setProx(aux);
                }
            }
        }
    }
    
    public Infofila dequeue() {
        Infofila ans = fila.getInfo();
        fila = fila.getProx();
        return ans;
    }
    
    public boolean isEmpty() {
        return fila == null;
    }
    
    public void exibe() {
        Nofila aux = fila;
        while (aux != null) {
            int[][] matriz = aux.getInfo().getInfo().getMatriz();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    System.out.println(matriz[i][j] + " ");
                }
                System.out.println("");
            }
            System.out.println("");
            aux = aux.getProx();
        }
    }
}
