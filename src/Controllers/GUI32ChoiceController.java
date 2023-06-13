package Controllers;

import Models.QuestionChoice;
import com.sun.javafx.binding.StringFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class GUI32ChoiceController implements Initializable {
    @FXML
    private ComboBox<String> gradeComboBox;
    @FXML
    private ImageView imageView;
    @FXML
    private TextArea textArea;

    public void getGradeComboBox(){
        QuestionChoice questionChoice = new QuestionChoice();
        ObservableList<String> grades = FXCollections.observableArrayList();
        for(int i=0;i<questionChoice.listGrade.size();i++){
            String gradeString=String.format("%.5f",questionChoice.listGrade.get(i));
            grades.add(gradeString+"%");
        }
        gradeComboBox.setItems(grades);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getGradeComboBox();
    }
}
