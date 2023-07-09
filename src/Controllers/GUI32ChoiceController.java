package Controllers;

import Models.Choice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class GUI32ChoiceController implements Initializable{
    @FXML
    private ComboBox<String> gradeComboBox;
    @FXML
    private ImageView imageView;
    @FXML
    private TextArea textArea;
    @FXML
    private Button insertPictureButton;
    @FXML
    private HBox imageHbox;

    public void setTextArea(String content){
        textArea.setText(content);
    }

    public void setImageView(Image image) {
        imageView.setImage(image);
    }

    public void setGradeComboBox(Double grade){
        ObservableList<String> grades = gradeComboBox.getItems();
        int index=-1;
        if(grade==0){
            gradeComboBox.getSelectionModel().select(0);
            return;
        }
        for(int i=1;i<grades.size();i++){
            String gradeString = grades.get(i);
            gradeString = gradeString.substring(0,gradeString.length()-1);
            double diff= Double.parseDouble(gradeString)/100-grade;//Round gradeString
            if(diff<0.0001){
                index=i;
                gradeComboBox.getSelectionModel().select(index);
                return;
            }
        }
    }
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
    public void getGradeComboBox(){
        Choice choice = new Choice();
        ObservableList<String> grades = FXCollections.observableArrayList();
        String none = new String("None");
        grades.add(none);
        for(int i = 0; i< choice.listGrade.size(); i++){
            String gradeString=String.format("%.5f", choice.listGrade.get(i));
            grades.add(gradeString+"%");
        }
        gradeComboBox.setItems(grades);
    }

    //Insert choice into Database
    public void insertChoice(int questionId){
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("insert into choice (question_id,content,grade,image) values (?,?,?,?);");

            String gradeString = gradeComboBox.getValue();
            double grade;
            if(gradeString == null || gradeString.equals("None")){
                grade=0.00000;
            }
            else{
                grade = Double.parseDouble(gradeString.substring(0,gradeString.length()-1));
                grade=grade/100.0;
            }

            Image image = imageView.getImage();
            InputStream inputStream = null;
            if(image != null){
                BufferedImage bufferedImage= SwingFXUtils.fromFXImage(image,null);
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage,"png", os);
                inputStream = new ByteArrayInputStream(os.toByteArray());
            }

            statement.setInt(1, questionId);
            statement.setString(2,textArea.getText());
            statement.setDouble(3,grade);
            statement.setBlob(4,inputStream);
            statement.executeUpdate();
            statement.close();
            connection.close();

            System.out.println("Inserted Choice Successfully");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //Remove all choices with question_id=questionID from Database
    public void removeChoice(int questionId){
        try{
            Connection connection = getConnection();
            String query="DELETE FROM test.choice WHERE question_id = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,questionId);
            statement.executeUpdate();
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Insert image into ImageView from file
    public void insertImage(){
        try {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(null);
            if (file!=null){
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getGradeComboBox();
        insertPictureButton.setOnAction(event -> insertImage());
    }
}