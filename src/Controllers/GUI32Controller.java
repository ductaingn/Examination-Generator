package Controllers;

import Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GUI32Controller implements Initializable {
    @FXML
    private Button home_btn;
    @FXML
    private Button gui21_btn;
    @FXML
    private Label switch_lbl;

    public void showGUI11() {
        Stage stage = (Stage)switch_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showGUI11();
    }
    public void showGUI21() {
        Stage stage = (Stage)switch_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showGUI21();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        home_btn.setOnAction(event -> showGUI11());
        gui21_btn.setOnAction(event -> showGUI21());
    }
}
