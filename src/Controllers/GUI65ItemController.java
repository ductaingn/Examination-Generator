package Controllers;

import Models.QQuestion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class GUI65ItemController implements Initializable {
    @FXML
    private Label name_lb;
    @FXML
    private Label text_lbl;
    @FXML
    private Label id_lbl;
    public void setData(QQuestion qQuestion){
        name_lb.setText(qQuestion.getName());
        text_lbl.setText(qQuestion.getText());
        id_lbl.setText(qQuestion.getQuestion_id()+"");
    }
    public static Vector<String> prepareToAdd = new Vector<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
