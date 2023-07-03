package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class GUI73itemNaviController {
    @FXML
    private VBox isSelected_vbox;
    @FXML
    private Label quesNo_navi_lbl;
    public void setQuesNo_navi_lbl(int i) {
        quesNo_navi_lbl.setText(String.valueOf(i));
    }
}
