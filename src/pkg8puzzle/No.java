package pkg8puzzle;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class No {
    private int[][] matriz;
    private int x, y, tl;
    private No parent;
    private No[] children;
    private List<Integer> possibilitiesList = new ArrayList<Integer>();

    public No(int[][] matriz, int x, int y) {
        this.matriz = matriz;
        this.x = x;
        this.y = y;
        children = new No[4];
    }
    
    private No(int[][] matriz, int x, int y, No parent){
        this.matriz = matriz;
        this.parent = parent;
        this.x = x;
        this.y = y;
        children = new No[4];
    }
    
    public void swap(int[][] mat, int x1, int y1, int x2, int y2){
        mat[x1][y1] = mat[x2][y2];
        mat[x2][y2] = 9;
    }
        
    public Point swapMatrix(int[][] mat, int factor){
        int nx = x, ny = y;
        switch(factor){
            case 0: swap(mat, x, y, --nx, ny);break;
            case 1: swap(mat, x, y, nx, ++ny);break;
            case 2: swap(mat, x, y, ++nx, ny);break;
            case 3: swap(mat, x, y, nx, --ny);break;
        }
        return new Point(nx, ny);
    }
    
    private void genMatrix(int[][] mat){
        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 3;j++){
                mat[i][j] = matriz[i][j];
            }
        }
    }
    
    public boolean equal(){
        int cont = 1, sum = 0;
        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 3;j++,cont++){
                if(matriz[i][j] == cont)
                    sum++;
            }
        }
        if(sum == 9)
            return true;
        else
            return false;
    }
    
    private boolean equal(int[][] mat, int x, int y){
        if(this.parent != null){
            swap(mat, this.x, this.y, x, y);
            int[][] parent = this.parent.getMatriz();
            int cont = 0;
            for(int i = 0;i < 3;i++){
                for(int j = 0;j < 3;j++){
                    if(mat[i][j] == parent[i][j])
                        cont++;
                }
            }
            swap(mat, x, y, this.x, this.y);
            if(cont == 9)
                return true;
            else
                return false;
        }
        else
            return false;
    }
    
    public void genPossibilitiesList() {
        possibilitiesList.clear();
        int[][] aux = new int[3][3];
        genMatrix(aux);
        //cima
        if (x - 1 > -1 && !equal(aux, x - 1, y)) {
            possibilitiesList.add(0);
        }
        //direita
        if (y + 1 < 3 && !equal(aux, x, y + 1)) {
            possibilitiesList.add(1);
        }
        //baixo
        if (x + 1 < 3 && !equal(aux, x + 1, y)) {
            possibilitiesList.add(2);
        }
        //esquerda
        if (y - 1 > -1 && !equal(aux, x, y - 1)) {
            possibilitiesList.add(3);
        }
    }
    
    public void genChildren(){
        int nx = 0, ny = 0;
        genPossibilitiesList();
        tl = possibilitiesList.size();
        Point p;
        for(int i = 0;i < tl;i++){
            int[][] aux = new int[3][3];
            for(int j = 0;j < 3;j++){
                for(int k = 0;k < 3;k++){
                    aux[j][k] = matriz[j][k];
                }
            }
            p = swapMatrix(aux, possibilitiesList.get(i));
            nx = p.x;
            ny = p.y;
            children[i] = new No(aux, nx, ny, this);
        }
    }
    
    private int getDistance(int value, int xf, int yf){
        int xi, yi;
        if (value % 3 != 0) {
            yi = value % 3 - 1;
            xi = value / 3;
        } else {
            xi = value / 3 - 1;
            yi = 2;
        }
        return Math.abs(xf - xi) + Math.abs(yf - yi);
    }
    
    public int getManhattanDistance(){
        int ans = 0;
        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 3;j++){
                ans += getDistance(matriz[i][j], i, j);
            }
        }
        ans -= getDistance(matriz[x][y], x, y);
        return ans;
    }

    public No getParent() {
        return parent;
    }
    
    public void exibe(){
        int[][] aux = matriz;
        int tl;
        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 3;j++){
                System.out.print(aux[i][j]+" ");
            }
            System.out.println("");
        }
        System.out.println("");
        tl = possibilitiesList.size();
        for(int i = 0;i < tl;i++){
            aux = children[i].getMatriz();
            for(int j = 0;j < 3;j++){
                for(int k = 0;k < 3;k++){
                    System.out.print(aux[j][k]+" ");
                }
                System.out.println("");
            }
            System.out.println("");
        }
    }

    public List<Integer> getPossibilitiesList() {
        return possibilitiesList;
    }

    public int[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(int[][] matriz) {
        this.matriz = matriz;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public No[] getChildren(){
        return children;
}


    public No getChildren(int pos) {
        return children[pos];
    }

    public void setChildren(int[][] matriz, int pos) {
        this.children[pos].setMatriz(matriz);
    }
    
    public int getTl() {
        return possibilitiesList.size();
    }
}
