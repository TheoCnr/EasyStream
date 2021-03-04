package main.views;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MediaPlaverView extends Application {
    @Override
    public void start(Stage primaryStage) {

        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("MediaPlayer.fxml"));

            Scene scene = new Scene(root, 300, 275);

            primaryStage.setTitle("FXML Welcome");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("hey hey hey");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
