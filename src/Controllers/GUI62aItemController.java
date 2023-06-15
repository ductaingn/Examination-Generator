package Controllers;

import Models.QQuestion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class GUI62aItemController implements Initializable {
    @FXML
    private Label rank_lbl;
    @FXML
    private Label name_lb;
    @FXML
    private Label text_lbl;
    public static int rank = 0;
    public void setData(QQuestion qQuestion){
        rank_lbl.setText(rank+1 +"");
        name_lb.setText(qQuestion.getName());
        text_lbl.setText(qQuestion.getText());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
