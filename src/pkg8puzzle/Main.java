package pkg8puzzle;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLIndex.fxml"));
        
        Scene scene = new Scene(root);
        
        Image icon = new Image("/icon/icon.png");
        
        stage.setTitle("8 Puzzle");
        
        stage.getIcons().add(icon);
        
        stage.setScene(scene);
        
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
