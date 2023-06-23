package Controllers;

import Models.QQuestion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class GUI73questionController implements Initializable {
    @FXML
    private Label question_text_lbl;
    @FXML
    private Label questionNo_lbl;
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
    public static String idData;
    public static int rank = 0;
    public void setData(QQuestion qQuestion) {
        questionNo_lbl.setText(rank+1 + "");
        question_text_lbl.setText(qQuestion.getText());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }
}
