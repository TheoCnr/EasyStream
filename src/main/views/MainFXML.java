package main.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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

    }

    private void getMovies()
    {
        list.removeAll(list);
        TheMovieDB.getIntance().refresh();
        for (Movie movie : TheMovieDB.getIntance().getMovies()) 
        {
            list.addAll(movie.getTitle());
        }
        first_scroll.getItems().addAll(list);
    }
}