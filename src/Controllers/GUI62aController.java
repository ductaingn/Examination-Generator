package Controllers;

import Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GUI62aController implements Initializable{
    @FXML
    private MenuItem fromQuesBank_mit;
    @FXML
    private Label switch_lbl;
    public void showGUI63a() {
        Stage stage = (Stage)switch_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showGUI63a();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fromQuesBank_mit.setOnAction(event -> showGUI63a());
    }
}
