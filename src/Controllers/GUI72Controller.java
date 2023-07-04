package Controllers;

import Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class GUI72Controller extends GUI74Controller implements Initializable {
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
    public static String idData, nameData, timeData;
    public String startTime;
    public void showGUI61() {
        Stage stage = (Stage)switch_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showGUI61();
    }
    public void showGUI73() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/GUI73.fxml"));
            Parent root = loader.load();
            GUI73Controller gui73Controller = loader.getController();
            gui73Controller.getInfo(idData, nameData, timeData);

            Stage stage = (Stage)switch_lbl.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
            Model.getInstance().getViewFactory().showGUI73();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void getInfo(String quiz_id, String quiz_name, String quiz_time) {
        idData = quiz_id;
        nameData = quiz_name;
        timeData = quiz_time;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cancel_btn.setOnAction(event -> showGUI61());
        close_btn.setOnAction(event -> showGUI61());
        start_btn.setOnAction(event -> {
        	showGUI73();
        });
    }
}
