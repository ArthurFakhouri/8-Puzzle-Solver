package pkg8puzzle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.awt.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class FXMLIndexController implements Initializable {

    @FXML
    private AnchorPane mainPanel;
    @FXML
    private GridPane grid;

    private No info;

    private int vezes, x, y, k, speed = 1000, nosExpandidos;

    private boolean last, play;

    private No auxNo;

    @FXML
    private JFXComboBox<String> amount;

    private List<int[][]> l;
    @FXML
    private JFXComboBox<String> algoritmos;
    @FXML
    private JFXComboBox<String> velocidade;
    @FXML
    private JFXButton shuffle;
    @FXML
    private JFXButton solve;
    @FXML
    private JFXButton reset;
    @FXML
    private Label winner;
    @FXML
    private Label situacao;
    @FXML
    private Label steps;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        genMatriz();
        genComboBox();
    }

    private void genMatriz() {
        int[][] matriz = new int[3][3];
        int cont = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matriz[i][j] = ++cont;
            }
        }
        x = y = 2;
        info = new No(matriz, x, y);
        updatePieces(matriz);
    }

    private void genComboBox() {
        ObservableList<String> amount = FXCollections.observableArrayList(
                "25x",
                "100x",
                "125x"
        );
        this.amount.setItems(amount);
        this.amount.getSelectionModel().select(0);
        ObservableList<String> algoritmos = FXCollections.observableArrayList(
                "A*",
                "Hill Climbing with Simulated Annealing"
        );
        this.algoritmos.setItems(algoritmos);
        this.algoritmos.getSelectionModel().select(0);
        ObservableList<String> velocidade = FXCollections.observableArrayList(
                "0.25 seconds",
                "0.50 seconds",
                "1.00 second",
                "1.25 seconds",
                "1.50 seconds",
                "2.00 seconds",
                "5.00 seconds",
                "10.00 seconds"
        );
        this.velocidade.setItems(velocidade);
        this.velocidade.getSelectionModel().select(2);
    }

    @FXML
    private void shuffle(MouseEvent event) {
        Random rand = new Random();
        int pos;
        steps.setVisible(false);
        Point p;
        genMatriz();
        List<int[][]> lm = new ArrayList();
        vezes = Integer.parseInt(amount.getValue().replaceAll("x", ""));
        for (int i = 0; i < vezes; i++) {
            info.genPossibilitiesList();
            pos = rand.nextInt(info.getTl());
            p = info.swapMatrix(info.getMatriz(), info.getPossibilitiesList().get(pos));
            info.setX(p.x);
            info.setY(p.y);
            x = info.getX();
            y = info.getY();
        }
        lm.add(info.getMatriz());
        updatePieces(info.getMatriz());
        play = true;
    }

    private void updatePieces(int[][] mat) {
        AnchorPane a;
        ImageView iv;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                a = (AnchorPane) grid.getChildren().get((i * 3) + j);
                iv = (ImageView) a.getChildren().get(2);
                iv.setImage(new Image(getClass().getResourceAsStream("public/images/0" + mat[i][j] + ".png")));
                if (mat[i][j] == 9 && !last) {
                    iv.setVisible(false);
                } else {
                    iv.setVisible(true);
                }
            }
        }
        last = false;
    }

    @FXML
    private void reset(MouseEvent event) {
        AnchorPane a;
        ImageView iv;
        for (int i = 0; i < grid.getChildren().size() - 1; i++) {
            a = (AnchorPane) grid.getChildren().get(i);
            iv = (ImageView) a.getChildren().get(2);
            iv.setImage(new Image(getClass().getResourceAsStream("public/images/0" + (i + 1) + ".png")));
            iv.setVisible(true);
        }
        genMatriz();
        play = false;
    }

    @FXML
    private void solveProblem(MouseEvent event) throws InterruptedException {
        String algoritmo = algoritmos.getValue();
        if (algoritmo.equals("A*")) {
            aStar();
        } else {
            hillClimbingWithSimulatedAnnealing();
        }
//        auxNo = new No(info.getMatriz(), info.getX(), info.getY());
//        int[][] matriz = auxNo.getMatriz();
//        for(int i = 0;i < 3;i++){
//            for(int j = 0;j < 3;j++){
//                System.out.print(matriz[i][j]+" ");
//            }
//            System.out.println("");
//        }
//        System.out.println(amount.getValue());
//        nosExpandidos = 0;
//        aStar();
//        nosExpandidos = 0;
//        hillClimbingWithSimulatedAnnealing();
//        play = false;
    }

    public double probability(double f1, double f2, double temp) {
        if (f2 < f1) {
            return 1;
        }
        return Math.exp((f1 - f2) / temp);
    }

    public void hillClimbingWithSimulatedAnnealing() {
        Pilha p = new Pilha();
        int mainDist, tl, temperatura = 1000;
        double coolingFactor = 0.995, t = temperatura;
        No traveler = new No(info.getMatriz(), info.getX(), info.getY()), bestWay;
        while (t > 1.0 && traveler != null && !traveler.equal()) {
            traveler.genChildren();
            Fila fila = new Fila();
            mainDist = traveler.getManhattanDistance();
            bestWay = traveler;
            tl = traveler.getTl();
            for (int i = 0; i < tl; i++) {
                fila.enqueue(traveler.getChildren(i), traveler.getChildren(i).getManhattanDistance());
            }
            bestWay = fila.dequeue().getInfo();
            if (bestWay.getManhattanDistance() < mainDist) {
                p.push(traveler, fila);
                traveler = bestWay;
                nosExpandidos++;
            } else {
                if (Math.random() < probability(mainDist, bestWay.getManhattanDistance(), temperatura)) {
                    p.push(traveler, fila);
                    traveler = bestWay;
                    nosExpandidos++;
                } else {
                    Infopilha ip;
                    if (!p.isEmpty()) {
                        ip = p.pop();
                        while (!p.isEmpty() && ip != null && ip.getFila().isEmpty()) {
                            ip = p.pop();
                        }
                        if (ip != null && !ip.getFila().isEmpty()) {
                            traveler = ip.getFila().dequeue().getInfo();
                            nosExpandidos++;
                            p.push(ip.getInfo(), ip.getFila());
                        }
                    } else {
                        traveler = null;
                    }
                }
            }
            t *= coolingFactor;
        }
        if ((p.getPilha() == null || t <= 1) && !traveler.equal()) {
            situacao.setText("Não foi possível!!");
            situacao.setVisible(true);
            Task task = new Task() {
                @Override
                protected Object call() throws Exception {
                    Thread.sleep(2000);
                    situacao.setVisible(false);
                    return null;
                }
            };
            new Thread(task).start();
        } else {
            System.out.println(++nosExpandidos);
            p.push(traveler, null);
            exibe(p.getAns());
        }
    }

    public void aStar() {
        Fila fila = new Fila();
        Pilha pilha = new Pilha();
        int custo = 0, mainDist, tl;
        Infofila inf;
        No traveler = new No(info.getMatriz(), info.getX(), info.getY());
        while (!traveler.equal()) {
            traveler.genChildren();
            mainDist = traveler.getManhattanDistance() + custo;
            custo++;
            tl = traveler.getTl();
            pilha.push(traveler, fila);
            for (int i = 0; i < tl; i++) {
                fila.enqueue(traveler.getChildren(i), custo, pilha.getPilha(), traveler.getChildren(i).getManhattanDistance() + custo);
            }
            inf = fila.dequeue();
            traveler = inf.getInfo();
            nosExpandidos++;
            custo = inf.getCusto();
            pilha = inf.getPilha();
        }
        pilha.push(traveler, fila);
        exibe(pilha.getAns());
        System.out.println(pilha.getAns().size());
        System.out.println(nosExpandidos);
    }

    public void exibe(List<int[][]> l) {
        solve.setDisable(true);
        reset.setDisable(true);
        shuffle.setDisable(true);
        steps.setVisible(true);
        Thread thread = new Thread(() -> {
            for (k = 0; k < l.size(); k++) {
                if (k + 1 == l.size()) {
                    last = true;
                    info.setMatriz(l.get(k));
                    x = y = 2;
                }
                Platform.runLater(() -> steps.setText((k + 1) + "/" + l.size()));
                Platform.runLater(() -> updatePieces(l.get(k)));
                try {
                    Thread.sleep((int) (speed / Double.parseDouble(velocidade.getValue().replaceAll(" second", "").replaceAll("s", ""))));
                } catch (InterruptedException ex) {
                    Logger.getLogger(FXMLIndexController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            solve.setDisable(false);
            reset.setDisable(false);
            shuffle.setDisable(false);
        });
        thread.start();
    }

    @FXML
    private void pieceTouched(MouseEvent event) {
        if (play) {
            AnchorPane a = (AnchorPane) event.getSource();
            int x = 0, y = 0;
            int value = Integer.parseInt(((Label) a.getChildren().get(0)).getText().replaceAll("p", ""));
            switch (value) {
                case 1:
                    x = y = 0;
                    break;
                case 2:
                    x = 0;
                    y = 1;
                    break;
                case 3:
                    x = 0;
                    y = 2;
                    break;
                case 4:
                    x = 1;
                    y = 0;
                    break;
                case 5:
                    x = y = 1;
                    break;
                case 6:
                    x = 1;
                    y = 2;
                    break;
                case 7:
                    x = 2;
                    y = 0;
                    break;
                case 8:
                    x = 2;
                    y = 1;
                    break;
                case 9:
                    x = y = 2;
                    break;
            }
            if ((Math.abs(this.x - x) + Math.abs(this.y - y)) == 1) {
                info.swap(info.getMatriz(), this.x, this.y, x, y);
                info.setX(x);
                info.setY(y);
                this.x = x;
                this.y = y;
                if (info.equal()) {
                    last = true;
                    winner.setVisible(true);
                    Task task = new Task() {
                        @Override
                        protected Object call() throws Exception {
                            Thread.sleep(2000);
                            winner.setVisible(false);
                            return null;
                        }
                    };
                    new Thread(task).start();
                }
                updatePieces(info.getMatriz());
            }
        }
    }
}
