package Controllers;

import Models.Model;
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

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class GUI11Controller implements Initializable{
    @FXML
    private TableView<Quiz> tableView;
    @FXML
    private TableColumn tv_icon;
    @FXML
    private TableColumn<Quiz, String> tv_name;
    @FXML
    private Button turnEditingOn_btn;
    @FXML
    private Label it_lbl;
    @FXML
    private MenuItem questions_btn;
    public void showGUI21() {
        Stage stage = (Stage)it_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showGUI21();
    }
    public void showGUI51() {
        Stage stage = (Stage)it_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showGUI51();
    }
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
    private void loadQuiz() {
        try {
            ObservableList<Quiz> quizList = FXCollections.observableArrayList();
            String query = "SELECT name FROM quiz";
            Connection connection = getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            while (resultSet.next()) {
                Quiz quiz = new Quiz();
                quiz.setQuizName(resultSet.getString("name"));
                quizList.add(quiz);
            }
            tv_name.setCellValueFactory((new PropertyValueFactory<>("quizName")));
            Callback<TableColumn<Quiz, String>, TableCell<Quiz, String>> cellFactory = (param) -> {
                final TableCell<Quiz, String> cell = new TableCell<Quiz, String>(){
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        }
                        else {
                            Image image = new Image("/resources/Image/notes.png",25, 25, true, true);
                            ImageView imageView = new ImageView(image);
                            HBox hBox = new HBox(imageView);
                            setGraphic(hBox);
                            setText(null);
                        }
                    };
                };
                return cell;
            };
            tv_icon.setCellFactory(cellFactory);
            tableView.setItems(quizList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadQuiz();
        questions_btn.setOnAction(event -> showGUI21());
        turnEditingOn_btn.setOnAction(event -> showGUI51());
    }
}
