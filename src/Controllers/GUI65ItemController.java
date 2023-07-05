package Controllers;

import Models.QQuestion;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class GUI65ItemController{
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
}
