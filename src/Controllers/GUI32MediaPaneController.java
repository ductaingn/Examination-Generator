package Controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
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
    private String mediaLink=null;
    public Connection getConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
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
        mediaPlayer.seek(Duration.ZERO);
        mediaPlayer.play();
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
    public void insertVideo(Integer questionId){
        try {
            Connection connection = getConnection();
            if(mediaLink!=null){
                PreparedStatement statement = connection.prepareStatement("UPDATE test.question SET mediaLink=? WHERE question_id=?;");
                statement.setString(1,mediaLink);
                statement.setInt(2,questionId);
                statement.executeUpdate();
                statement.close();
            }
            else {
                Statement statement = connection.createStatement();
                String query = "UPDATE test.question SET mediaLink = NULL WHERE question_id = '" + questionId + "';";
                statement.executeUpdate(query);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playButton.setOnAction(event -> playMedia());
        pauseButton.setOnAction(event -> pauseMedia());
        replayButton.setOnAction(event -> replayMedia());
        removeVideoButton.setOnAction(event -> removeVideo());
    }
}