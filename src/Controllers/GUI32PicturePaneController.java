package Controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class GUI32PicturePaneController implements Initializable {
    @FXML
    private ImageView imageView;
    @FXML
    private Button removePictureButton;
    private Image image;
    private GUI32paneController parentController;
    private File imageFile=null;
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
    public void setImageView(){
        try {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(null);
            if (file!=null){
                image = new Image(file.toURI().toString());
                imageView.setImage(image);
            }
            InputStream inputStream = new FileInputStream(file);
            imageFile=file;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void setImageView(InputStream inputStream){
        image = new Image(inputStream);
        imageView.setImage(image);
    }

    public void setImageView(Image image) {
        this.image = image;
        imageView.setImage(image);
    }

    public Image getImage(){
        return image;
    }
    public void removePicture(){
        image = null;
        parentController.removePicture();
    }
    public void insertPicture(Integer questionId){
        try {
            Connection connection = getConnection();
            if(imageFile!=null){
                InputStream inputStream = new FileInputStream(imageFile);
                PreparedStatement statement = connection.prepareStatement("UPDATE test.question SET image=? WHERE question_id=?;");
                statement.setBlob(1,inputStream);
                statement.setInt(2,questionId);
                statement.executeUpdate();
                statement.close();
            } else if (imageFile==null && imageView.getImage()!=null) {
                return;
            } else{
                PreparedStatement statement = connection.prepareStatement("UPDATE test.question SET image=NULL WHERE question_id=?;");
                statement.setInt(1,questionId);
                statement.executeUpdate();
                statement.close();
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        removePictureButton.setOnAction(event -> removePicture());
    }
}
