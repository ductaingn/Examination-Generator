package Controllers;

import Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GUI32paneController implements Initializable {
    @FXML
    private Label switch_lbl;
    @FXML
    private Button blanks_btn;
    @FXML
    private Button saveAndContinue_btn;
    @FXML
    private Button saveChanges_btn;
    @FXML
    private Button cancel_btn;

    public void showGUI21() {
        Stage stage = (Stage)switch_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showGUI21();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cancel_btn.setOnAction(event -> showGUI21());
        saveChanges_btn.setOnAction(event -> showGUI21());
    }
}
