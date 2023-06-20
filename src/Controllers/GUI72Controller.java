package Controllers;

import Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GUI72Controller implements Initializable {
    @FXML
    private Button cancel_btn;
    @FXML
    private Button close_btn;
    @FXML
    private Button start_btn;
    @FXML
    private Button export_btn;
    @FXML
    private Label switch_lbl;

    public void showGUI61() {
        Stage stage = (Stage)switch_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showGUI61();
    }
    public void showGUI73() {
        Stage stage = (Stage)switch_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showGUI73();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cancel_btn.setOnAction(event -> showGUI61());
        close_btn.setOnAction(event -> showGUI61());
        start_btn.setOnAction(event -> showGUI73());
    }
}
