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

public class GUI62aController implements Initializable{
    @FXML
    private Button quiz_btn;
    @FXML
    private Button home_btn;
    @FXML
    private MenuItem fromQuesBank_mit;
    @FXML
    private Label switch_lbl;
    @FXML
    private Label title_lbl;
    @FXML
    private Label title2_lbl;
    public void showGUI11() {
        Stage stage = (Stage)switch_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showGUI11();
    }
    public void showGUI61() {
        Stage stage = (Stage)switch_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showGUI61();
    }
    public void showGUI63a() {
        Stage stage = (Stage)switch_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showGUI63a();
    }

    public static String nameData;
    public void getTitle(String quizName) {
        nameData = quizName;
        title_lbl.setText("Editting quiz: " + quizName);
        title2_lbl.setText(quizName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getTitle(nameData);
        home_btn.setOnAction(event -> showGUI11());
        quiz_btn.setOnAction(event -> showGUI61());
        fromQuesBank_mit.setOnAction(event -> showGUI63a());
    }
}
