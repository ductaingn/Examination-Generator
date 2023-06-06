package Controllers;

import Models.QQuestion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class GUI63aItemController implements Initializable {
    @FXML
    private Label name_lb;
    @FXML
    private Label text_lbl;

    public void setData(QQuestion qQuestion){
        name_lb.setText(qQuestion.getName());
        text_lbl.setText(qQuestion.getText());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }
}
