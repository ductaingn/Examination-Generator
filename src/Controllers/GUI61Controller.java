package Controllers;

import Models.Model;
import Models.Quiz;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class GUI61Controller implements Initializable{
    @FXML
    private Button gear_btn;
    @FXML
    private Label switch_lbl;
    @FXML
    private Label timeLimit_lbl;
    @FXML
    private Label title2_lbl;
    @FXML
    private Label title_lbl;
    public void showGUI62a() {
        Stage stage = (Stage)switch_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showGUI62a();
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

//    static để data không bị trả về null
    private static String nameData;
    private static String timeData;
    public void getAllLabel(String quizName, String quizTime) {
        nameData = quizName;
        timeData = quizTime;
        title_lbl.setText(quizName);
        title2_lbl.setText(quizName);
        timeLimit_lbl.setText(quizTime);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAllLabel(nameData, timeData);
        gear_btn.setOnAction(event -> showGUI62a());
    }
}
