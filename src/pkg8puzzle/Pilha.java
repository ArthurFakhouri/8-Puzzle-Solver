package pkg8puzzle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Pilha {

    private Nopilha pilha;

    public Pilha() {
        pilha = null;
    }

    public Pilha(Nopilha pilha) {
        this.pilha = pilha;
    }

    public void push(No info, Fila fila) {
        Infopilha ip = new Infopilha(info, fila);
        pilha = new Nopilha(ip, pilha);
    }

    public Infopilha pop() {
        Infopilha ans = pilha.getInfo();
        pilha = pilha.getProx();
        return ans;
    }

    public boolean isEmpty() {
        return pilha == null;
    }

    public Nopilha getPilha() {
        return pilha;
    }

    public List<int[][]> getAns() {
        Nopilha aux = pilha;
        List<int[][]> l = new ArrayList();
        while (aux != null) {
            int[][] matriz = aux.getInfo().getInfo().getMatriz();
            l.add(matriz);
            aux = aux.getProx();
        }
        Collections.reverse(l);
        return l;
    }
}
