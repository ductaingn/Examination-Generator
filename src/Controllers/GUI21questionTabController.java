package Controllers;

import Models.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class GUI21questionTabController extends GUI21Controller implements Initializable {
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private Label SW_lbl;
    @FXML
    private javafx.scene.control.Button createNewQuest_btn;
    public void showGUI32() {
        Stage stage = (Stage)SW_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showGUI32();
    }
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
//    connect database
    public Connection getConnection() {
        try {
//            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void getComboBox() {
        String queryCategoryName = "" +
                "SELECT CONCAT( REPEAT('-', COUNT(parent.name) - 1), node.name) AS name " +
                "FROM category AS node," +
                "category AS parent " +
                "WHERE node.lft BETWEEN parent.lft AND parent.rgt " +
                "GROUP BY node.name ORDER BY node.lft;";
        connection = getConnection();
        try {
            preparedStatement = connection.prepareStatement(queryCategoryName);
            resultSet = preparedStatement.executeQuery();
            ObservableList<String> categoryName = FXCollections.observableArrayList();
            while (resultSet.next()) {
                String item = resultSet.getString("name");
                categoryName.add(item);
            }
            comboBox.setItems(categoryName);
        } catch (Exception e) {e.printStackTrace();}
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getComboBox();
        createNewQuest_btn.setOnAction(event -> showGUI32());
    }
}
