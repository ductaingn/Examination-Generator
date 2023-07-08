package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class GUI32MediaPaneController implements Initializable {
    @FXML
    private MediaView mediaView;

    @FXML
    private Button pauseButton;

    @FXML
    private Button playButton;

    @FXML
    private Button replayButton;
    @FXML
    private Button removeVideoButton;
    private GUI32paneController parentController;
    private Media media;
    private MediaPlayer mediaPlayer;
    private String mediaLink;
    public void setParentController(GUI32paneController gui32paneController){
        this.parentController=gui32paneController;
    }
    public void playMedia(){
        mediaPlayer.play();
    }
    public void pauseMedia(){
        mediaPlayer.pause();
    }
    public void replayMedia(){
    }

    public String getMediaLink() {
        return mediaLink;
    }

    public void setMediaLink(String mediaLink) {
        this.mediaLink = mediaLink;
    }

    public void setMediaView() {
        try {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(null);
            if (file!=null){
                media= new Media(file.toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaView.setMediaPlayer(mediaPlayer);
                setMediaLink(file.toURI().toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void setMediaView(String link){
        media=new Media(link);
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        setMediaLink(link);
    }

    private void removeVideo() {
        mediaLink=null;
        parentController.removeVideo();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playButton.setOnAction(event -> playMedia());
        pauseButton.setOnAction(event -> pauseMedia());
        removeVideoButton.setOnAction(event -> removeVideo());
    }

}