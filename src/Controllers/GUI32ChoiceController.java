package Controllers;

import Models.Choice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
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

    private FileInputStream fileInputStream;
    public Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "0000");
            return conn;
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
    public void insertChoice(int questionId){
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("insert into choice (question_id,content,grade,image) values (?,?,?,?);");

            String gradeString = new String(gradeComboBox.getValue());
            Double grade;
            if(gradeString.equals("None")){
                grade=0.00000;
            }
            else{
                grade = Double.parseDouble(gradeString.substring(0,gradeString.length()-1));
                grade=grade/100.0;
            }

            statement.setInt(1, questionId);
            statement.setString(2,textArea.getText());
            statement.setDouble(3,grade);
            statement.setBlob(4,fileInputStream);
            statement.executeUpdate();

            System.out.println("Inserted Choice Successfully");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void insertImage(){
        try {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(null);
            if (file!=null){
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);
            }
            fileInputStream = new FileInputStream(file);
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
