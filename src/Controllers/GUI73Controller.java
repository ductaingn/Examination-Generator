package Controllers;

import Models.Model;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class GUI73Controller extends GUI61Controller implements Initializable {
    @FXML
    private Button home_btn;
    @FXML
    private Label switch_lbl;
    @FXML
    private Label title2_lbl;

    public void showGUI11() {
        Stage stage = (Stage)switch_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showGUI11();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("time: " + timeData);
        System.out.println("quizID: " + idData);
        title2_lbl.setText(nameData);
        home_btn.setOnAction(event -> showGUI11());
    }
}
