package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class GUI21categoriesTabController implements Initializable {
    @FXML
    private Button addCategory_btn;
    @FXML
    private TextField id_fld;
    @FXML
    private TextArea info_fld;
    @FXML
    private TextField name_fld;
    @FXML
    private ComboBox<String> parentCategory_comb;

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
        String queryCategoryName =
                "SELECT CONCAT( REPEAT(' ', COUNT(parent.name) - 1), node.name) AS name " +
                "FROM category AS node," +
                "category AS parent " +
                "WHERE node.lft BETWEEN parent.lft AND parent.rgt " +
                "GROUP BY node.category_id ORDER BY node.lft;";
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryCategoryName);
            ResultSet resultSet = preparedStatement.executeQuery();
            ObservableList<String> categoryName = FXCollections.observableArrayList();
            while (resultSet.next()) {
                String item = resultSet.getString("name");
                categoryName.add(item);
            }
            parentCategory_comb.setItems(categoryName);
        } catch (Exception e) {e.printStackTrace();}
    }

    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void insertCategory() {
        String queryInsertCategory =
                "LOCK TABLES category WRITE; " +
                "SELECT @myRight := rgt FROM category " +
                "WHERE name = '" + parentCategory_comb.getValue().trim() + "'; " +
                "UPDATE category SET rgt = rgt + 2 WHERE rgt > @myRight; " +
                "UPDATE category SET lft = lft + 2 WHERE lft > @myRight; " +
                "INSERT INTO category(category_id, name, info, lft, rgt) VALUES(" + id_fld.getText() + ", '" + name_fld.getText() + "', '" + info_fld.getText() + "', @myRight, @myRight + 1); " +
                "UNLOCK TABLES;" +
                "UPDATE category SET rgt = rgt + 2 WHERE name = '" + parentCategory_comb.getTypeSelector() + "';";
        executeQuery(queryInsertCategory);
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == addCategory_btn){
            insertCategory();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getComboBox();

    }
}