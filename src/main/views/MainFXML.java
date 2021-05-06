package main.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.Movie;
import main.TheMovieDB;

public class MainFXML {

    ObservableList<String> list = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton browse_btn;

    @FXML
    private JFXListView<String> first_scroll;

    @FXML
    void initialize() {
        assert browse_btn != null : "fx:id=\"browse_btn\" was not injected: check your FXML file 'main.fxml'.";
        assert first_scroll != null : "fx:id=\"first_scroll\" was not injected: check your FXML file 'main.fxml'.";
        getMovies();
        browse_btn.setOnAction(e -> file_choose(e));
    }

    private void getMovies() {
        list.removeAll(list);
        TheMovieDB.getIntance().refresh();
        for (Movie movie : TheMovieDB.getIntance().getMovies()) {
            list.addAll(movie.getTitle());
        }
        first_scroll.getItems().addAll(list);
    }

    public void file_choose(ActionEvent click) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            VlcjJavaFxApplication player = new VlcjJavaFxApplication();
            Stage primaryStage = (Stage) ((Node)click.getSource()).getScene().getWindow();
            player.init();
            player.setFile(selectedFile);
            try {
                player.start(primaryStage);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        else {
            System.out.println("error file");
        }
    }
}
