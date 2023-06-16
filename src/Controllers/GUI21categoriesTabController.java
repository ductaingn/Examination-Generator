package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class GUI21categoriesTabController implements Initializable {
    @FXML
    private Button delCategory_btn;
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

    public Connection getConnection() {
        Connection connection;
        try {
//            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void getComboBox() {
        String queryCategoryName =""+
                "SELECT CONCAT( REPEAT(' ', COUNT(parent.name) - 1),' ' ,node.name,' (', " +
                "(SELECT COUNT(question_id) FROM question " +
                "WHERE question.category_id=node.category_id),') '  ) AS name " +
                "FROM category AS node,category AS parent " +
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void insertCategory() {
        try {
            String parentCategory = parentCategory_comb.getValue().trim();
            parentCategory = parentCategory.substring(0, parentCategory.indexOf("(") - 1);
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeQuery("LOCK TABLES category WRITE; ");
            statement.executeQuery("SELECT @myRight := rgt FROM category " +
                    "WHERE name = '" + parentCategory + "'; ");
            statement.executeUpdate("UPDATE category SET rgt = rgt + 2 WHERE rgt > @myRight; ");
            statement.executeUpdate("UPDATE category SET lft = lft + 2 WHERE lft > @myRight;");
            if (Objects.equals(id_fld.getText(), "")) {
                statement.executeUpdate("INSERT INTO category(category_id, name, info, lft, rgt) VALUES(null, '" + name_fld.getText() + "', '" + info_fld.getText() + "', @myRight, @myRight + 1); ");
            } else statement.executeUpdate("INSERT INTO category(category_id, name, info, lft, rgt) VALUES(" + id_fld.getText() + ", '" + name_fld.getText() + "', '" + info_fld.getText() + "', @myRight, @myRight + 1); ");
            statement.executeQuery("UNLOCK TABLES;");
            statement.executeUpdate("UPDATE category SET rgt = rgt + 2 WHERE name = '" + parentCategory + "';");
            getComboBox();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void deleteCategory() {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeQuery("LOCK TABLES category WRITE; ");
            statement.executeQuery("SELECT @myLeft := lft, @myRight := rgt, @myWidth := rgt - lft + 1 FROM category " +
                    "WHERE name = '" + name_fld.getText() + "';");
            statement.executeUpdate("DELETE FROM category WHERE lft BETWEEN @myLeft AND @myRight;");
            statement.executeUpdate("UPDATE category SET rgt = rgt - @myWidth WHERE rgt > @myRight;");
            statement.executeUpdate("UPDATE category SET lft = lft - @myWidth WHERE lft > @myRight;");
            statement.executeQuery("UNLOCK TABLES;");
            getComboBox();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == addCategory_btn) {
            insertCategory();
            System.out.println("Inserted Category");
        }
        else if (event.getSource() == delCategory_btn) {
            deleteCategory();
            System.out.println("Deleted Category");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getComboBox();
    }
}