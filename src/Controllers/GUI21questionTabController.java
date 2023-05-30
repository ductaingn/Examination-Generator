package Controllers;

import Models.Model;
import Models.Question;
import Models.Quiz;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.event.MouseEvent;
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
    @FXML
    private TableView<Question> tableView;
    @FXML
    private TableColumn<Question, String> tv_actions;
    @FXML
    private TableColumn<Question, String> tv_question;
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
                "SELECT CONCAT( REPEAT(' ', COUNT(parent.name) - 1), node.name) AS name " +
                "FROM category AS node," +
                "category AS parent " +
                "WHERE node.lft BETWEEN parent.lft AND parent.rgt " +
                "GROUP BY node.category_id ORDER BY node.lft;";
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
    private void loadQuestion() {
        try {
            ObservableList<Question> questionsList = FXCollections.observableArrayList();
            String query = "SELECT name FROM question";
            Connection connection = getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            while (resultSet.next()) {
                Question question = new Question();
                question.setQuestionName(resultSet.getString("name"));
                questionsList.add(question);
            }
            tv_question.setCellValueFactory((new PropertyValueFactory<>("questionName")));
            Callback<TableColumn<Question, String>, TableCell<Question, String>> cellFactory = (param) -> {
                final TableCell<Question, String> cell = new TableCell<Question, String>(){
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        }
                        else {
                            Label edit_lbl = new Label("Edit");
                            edit_lbl.setStyle(
                                    "-fx-text-fill: blue; -fx-font-size: 1em;"
                            );
//                            adding edit function
                            HBox hBox = new HBox(edit_lbl);
                            setGraphic(hBox);
                            setText(null);
                        }
                    };
                };
                return cell;
            };
            tv_actions.setCellFactory(cellFactory);
            tableView.setItems(questionsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadQuestion();
        getComboBox();
        createNewQuest_btn.setOnAction(event -> showGUI32());
    }
}
