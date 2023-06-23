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
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class GUI61Controller implements Initializable{
    @FXML
    private Button home_btn;
    @FXML
    private Button gear_btn;
    @FXML
    private Label switch_lbl;
    @FXML
    private Button preview_btn;
    @FXML
    private Label timeLimit_lbl;
    @FXML
    private Label title2_lbl;
    @FXML
    private Label title_lbl;
    //    static để data không bị trả về null
    public static String nameData;
    public static String timeData;
    public static String idData;
    public void showGUI11() {
        Stage stage = (Stage)switch_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showGUI11();
    }
    public void showGUI62a() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/GUI62a.fxml"));
            Parent root = loader.load();
            GUI62aController gui62aController = loader.getController();
            gui62aController.getTitle(nameData);
            gui62aController.getQuestionList(idData);

            Stage stage = (Stage) switch_lbl.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
            Model.getInstance().getViewFactory().showGUI62a();
        } catch (Exception e) {e.printStackTrace();}
    }
    public void showGUI72() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/GUI72.fxml"));
            Parent root = loader.load();
            GUI72Controller gui72Controller = loader.getController();
            gui72Controller.getInfo(idData, nameData, timeData);

            Stage stage = (Stage)switch_lbl.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
            Model.getInstance().getViewFactory().showGUI72();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Connection getConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void getAllLabel(String quizName, String quizTime, String quizId) {
        idData = quizId;
        nameData = quizName;
        timeData = quizTime;
        title_lbl.setText(quizName);
        title2_lbl.setText(quizName);
        timeLimit_lbl.setText(quizTime);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAllLabel(nameData, timeData, idData);
        home_btn.setOnAction(event -> showGUI11());
        gear_btn.setOnAction(event -> showGUI62a());
        preview_btn.setOnAction(event -> showGUI72());
    }
}
