package main.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import org.apache.commons.codec.binary.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
//import java.awt.Image;
import javafx.scene.image.Image;
import java.awt.image.BufferedImage;

import javafx.stage.DirectoryChooser;
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
    private JFXListView<String> first_scroll = new JFXListView<String>();

    @FXML
    private JFXListView<Movie> sec_scroll;

    @FXML
    private JFXButton find_mov;

    @FXML public void handleMouseClick(MouseEvent arg0) {
        if(arg0.getClickCount() == 2)
        {
            VlcjJavaFxApplication player = new VlcjJavaFxApplication();
            Stage primaryStage = (Stage) ((Node) arg0.getSource()).getScene().getWindow();
            player.init();
            player.setFile(new File(sec_scroll.getSelectionModel().getSelectedItem().getPath()));
            try {
                player.start(primaryStage);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            System.out.println("Double clicked");
            System.out.println("clicked on " + sec_scroll.getSelectionModel().getSelectedItem().getPath());
        }
        
        
    }

    @FXML
    void initialize() {
        assert browse_btn != null : "fx:id=\"browse_btn\" was not injected: check your FXML file 'main.fxml'.";
        assert find_mov != null : "fx:id=\"browse_btn\" was not injected: check your FXML file 'main.fxml'.";
        assert first_scroll != null : "fx:id=\"first_scroll\" was not injected: check your FXML file 'main.fxml'.";
        assert sec_scroll != null : "fx:id=\"sec_scroll\" was not injected: check your FXML file 'main.fxml'.";
        getMovies(first_scroll);
        find_mov.setOnAction(e -> directoryChooser(e));
        browse_btn.setOnAction(e -> file_choose(e));
    }

    private void directoryChooser(ActionEvent click)
    {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage primaryStage = (Stage) ((Node) click.getSource()).getScene().getWindow();
        File file = directoryChooser.showDialog(primaryStage);

        if (file != null) 
        {
            System.out.println(file.getAbsolutePath() + "\n \n \n");
            findMovies(file);
        }
    }

    private void findMovies(File folder)
    {
        for (File file : folder.listFiles()) 
        {
            if (!file.isDirectory()) 
            {   
                String title = "";
                String year = "";
                
                for (char c : file.getName().toCharArray()) 
                {
                    if (year.length() > 0 && year.length() < 4 && c == '.') 
                    {
                        title += year;
                        year = "";
                    }
                    if (year.length() == 4) 
                    {
                        break;
                    }
                    if (c >= '0' && c <= '9') 
                    {
                        year += c;
                    }
                    else
                    {
                        title += c;
                    }
                }
                title = title.replace(".", " ");

                System.out.println(title);
                System.out.println(year);

                ObservableList<Movie> list = FXCollections.observableArrayList();

                Movie film = TheMovieDB.getIntance()
                                .search(title, Integer.parseInt(year));
                film.setPath(file.getAbsolutePath());
                list.removeAll(list);
                list.addAll(film);
                sec_scroll.getItems().addAll(list);

                sec_scroll.setCellFactory(listView -> new ListCell<Movie>() {
                    private ImageView imageView = new ImageView();
        
                    @Override
                    public void updateItem(Movie item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
        
                        } else {
                            Image img;
                            img = new Image("https://image.tmdb.org/t/p/w500/" + item.getThumUrl());
                            imageView.setImage(img);
                            imageView.setFitHeight(180);
                            imageView.setPreserveRatio(true);
                            setGraphic(imageView);
        
                        }
                    }
                });
            }
        }
    }

    private void getMovies(JFXListView<String> scroll) {
        list.removeAll(list);
        TheMovieDB.getIntance().refresh();
        for (Movie movie : TheMovieDB.getIntance().getMovies()) {
            list.addAll(movie.getThumUrl());
        }

        scroll.getItems().addAll(list);

        // for (Movie movie : TheMovieDB.getIntance().getMovies()) {
        // System.out.println("https://image.tmdb.org/t/p/w500/" + movie.getThumUrl());
        // }

        scroll.setCellFactory(listView -> new ListCell<String>() {
            private ImageView imageView = new ImageView();

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);

                } else {
                    // BufferedImage img;
                    Image img;
                    // img = ImageIO.read(new URL("https://image.tmdb.org/t/p/w500/" +
                    // movie.getThumUrl()));
                    // System.out.println("https://image.tmdb.org/t/p/w500/" + movie.getThumUrl());
                    // javafx.scene.image.Image image = SwingFXUtils.toFXImage(img, null);
                    img = new Image("https://image.tmdb.org/t/p/w500/" + item);
                    imageView.setImage(img);
                    imageView.setFitHeight(180);
                    imageView.setPreserveRatio(true);
                    setGraphic(imageView);

                }
            }
        });
    }

    public void file_choose(ActionEvent click) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            VlcjJavaFxApplication player = new VlcjJavaFxApplication();
            Stage primaryStage = (Stage) ((Node) click.getSource()).getScene().getWindow();
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
