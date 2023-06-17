package Controllers;

import Models.Model;
import Models.QQuestion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

public class GUI63aController extends GUI63aItemController implements Initializable {
    @FXML
    private VBox listQues;
    @FXML
    private Button add_btn;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private Button close_btn;
    @FXML
    private Label switch_lbl;
    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void getComboBox() {
        String queryCategoryName = ""+
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
            comboBox.setItems(categoryName);
        } catch (Exception e) {e.printStackTrace();}
    }
    public void showGUI62a(){
        Stage stage = (Stage) switch_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showGUI62a();
    }
    public void showGUI62a_add() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/GUI62a.fxml"));
            Parent root = loader.load();
            GUI62aController gui62aController = loader.getController();
            Vector<String> quesList = prepareToAdd;
            gui62aController.insertQuesToQuiz(quesList);

            Stage stage = (Stage) switch_lbl.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
            Model.getInstance().getViewFactory().showGUI62a();
        } catch (Exception e) {e.printStackTrace();}
    }
    private List<QQuestion> qQuestionList(){
        Connection connection = getConnection();
        String query = "SELECT * FROM question";
        List<QQuestion> list = new ArrayList<>();
        QQuestion qQuestion;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                qQuestion = new QQuestion();
                qQuestion.setName(resultSet.getString("name"));
                qQuestion.setText(resultSet.getString("text"));
                qQuestion.setQuestion_id(resultSet.getInt("question_id"));
                list.add(qQuestion);
            }
        } catch (Exception e) {e.printStackTrace();}
        return list;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getComboBox();
        close_btn.setOnAction(event -> {
            showGUI62a();
            prepareToAdd.clear();
        });
        add_btn.setOnAction(event -> {
            showGUI62a_add();
            System.out.println(prepareToAdd.size());
            prepareToAdd.clear();
        });

        List<QQuestion> qQuestionList = new ArrayList<>(qQuestionList());
        for (int i = 0; i < qQuestionList.size(); i++) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/resources/Fxml/GUI63aItem.fxml"));
            try {
                HBox hBox = loader.load();
                GUI63aItemController itemController = loader.getController();
                itemController.setData(qQuestionList.get(i));
                listQues.getChildren().add(hBox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
