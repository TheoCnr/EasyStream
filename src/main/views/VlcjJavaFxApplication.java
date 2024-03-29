package main.views;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

//import java.util.List;

import static uk.co.caprica.vlcj.javafx.videosurface.ImageViewVideoSurfaceFactory.videoSurfaceForImageView;

import java.io.File;

/**
 *
 */
public class VlcjJavaFxApplication extends Application {

    private final MediaPlayerFactory mediaPlayerFactory;

    private final EmbeddedMediaPlayer embeddedMediaPlayer;

    private ImageView videoImageView;

    private File file;


    public MediaPlayerFactory getMediaPlayerFactory() {
        return this.mediaPlayerFactory;
    }
    public EmbeddedMediaPlayer getEmbeddedMediaPlayer() {
        return this.embeddedMediaPlayer;
    }
    public ImageView getVideoImageView() {
        return this.videoImageView;
    }


    public VlcjJavaFxApplication() {
        this.mediaPlayerFactory = new MediaPlayerFactory();
        this.embeddedMediaPlayer = mediaPlayerFactory.mediaPlayers().newEmbeddedMediaPlayer();
        this.embeddedMediaPlayer.events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void playing(MediaPlayer mediaPlayer) {
            }

            @Override
            public void paused(MediaPlayer mediaPlayer) {
            }

            @Override
            public void stopped(MediaPlayer mediaPlayer) {
            }

            @Override
            public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
            }
        });
    }

    @Override
    public void init() {
        this.videoImageView = new ImageView();
        this.videoImageView.setPreserveRatio(true);

        embeddedMediaPlayer.videoSurface().set(videoSurfaceForImageView(this.videoImageView));
    }

    public void setFile(File file)
    {
        this.file = file;
    }

    @Override
    public final void start(Stage primaryStage) throws Exception {
        // List<String> params = getParameters().getRaw();
        // System.out.println("HAAAAAAAAAAAAAAAAAAAAAAAAAA : " + params.get(0).toString());
        // if (params.size() != 1) {
        //     System.out.println("Specify a single MRL");
        //     System.exit(-1);
        // }

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: black;");

        videoImageView.fitWidthProperty().bind(root.widthProperty());
        videoImageView.fitHeightProperty().bind(root.heightProperty());

        // root.widthProperty().addListener((observableValue, oldValue, newValue) -> {
        //     // If you need to know about resizes
        // });

        // root.heightProperty().addListener((observableValue, oldValue, newValue) -> {
        //     // If you need to know about resizes
        // });

        root.setCenter(videoImageView);

        Scene scene = new Scene(root, 1200, 675, Color.BLACK);
        primaryStage.setTitle("vlcj JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();

        //embeddedMediaPlayer.media().play(params.get(0));
        //embeddedMediaPlayer.media().play("/Users/theocanario/Downloads/Titanic - 1080p FR EN x264 ac3 mHDgz.mkv");
        embeddedMediaPlayer.media().play(file.getAbsolutePath());

        embeddedMediaPlayer.controls().setPosition(0.4f);
    }

    @Override
    public final void stop() {
        embeddedMediaPlayer.controls().stop();
        embeddedMediaPlayer.release();
        mediaPlayerFactory.release();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
