package Controllers;

import Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GUI11Controller implements Initializable{
    @FXML private Button turnEditingOn_btn;
    @FXML private Label it_lbl;
    @FXML private MenuItem questions_btn;


    public void showGUI21() {
        Stage stage = (Stage)it_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showGUI21();
    }
    public void showGUI51() {
        Stage stage = (Stage)it_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showGUI51();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        questions_btn.setOnAction(event -> showGUI21());
        turnEditingOn_btn.setOnAction(event -> showGUI51());
    }
}
