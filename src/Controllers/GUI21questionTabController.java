package Controllers;

import Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class GUI21questionTabController extends GUI21Controller implements Initializable {
    @FXML
    private Label SW_lbl;
    @FXML
    private javafx.scene.control.Button createNewQuest_btn;
    public void showGUI32() {
        Stage stage = (Stage)SW_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showGUI32();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createNewQuest_btn.setOnAction(event -> showGUI32());
    }
}
