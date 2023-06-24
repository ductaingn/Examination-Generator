package Controllers;

import Models.Choice;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class GUI73choiceController implements Initializable {
    @FXML
    public RadioButton choice_content_rbt;

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
    public void setChoiceData(Choice choice) {
        choice_content_rbt.setText(choice.getChoiceText());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
