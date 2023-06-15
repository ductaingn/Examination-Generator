package Controllers;

import Models.Model;
import Models.QQuestion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
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

public class GUI62aController implements Initializable{
    @FXML
    private MenuItem aRandomQues_mit;
    @FXML
    private VBox listQues;
    @FXML
    private Label numOfQues_lbl;
    @FXML
    private Label totalMark_lbl;
    @FXML
    private Button quiz_btn;
    @FXML
    private Button home_btn;
    @FXML
    private MenuItem fromQuesBank_mit;
    @FXML
    private Label switch_lbl;
    @FXML
    private Label title_lbl;
    @FXML
    private Label title2_lbl;
    private static String nameData;
    private static String idData;
    public void showGUI11() {
        Stage stage = (Stage)switch_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showGUI11();
    }
    public void showGUI61() {
        Stage stage = (Stage)switch_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showGUI61();
    }
    public void showGUI63a() {
        Stage stage = (Stage)switch_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showGUI63a();
    }
    public void showGUI65() {
        Stage stage = (Stage)switch_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showGUI65();
    }
   //show question list
    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private List<QQuestion> qQuestionList(){
        Connection connection = getConnection();
        String query = "SELECT name, text from ques_quiz, question " +
                "WHERE ques_quiz.question_id = question.question_id and quiz_id = " + idData;
        List<QQuestion> list = new ArrayList<>();
        QQuestion qQuestion;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                qQuestion = new QQuestion();
                qQuestion.setName(resultSet.getString("name"));
                qQuestion.setText(resultSet.getString("text"));
                list.add(qQuestion);
            }
        } catch (Exception e) {e.printStackTrace();}
        return list;
    }
    public void getQuestionList(String quizId){
        idData = quizId;
        List<QQuestion> qQuestionList = new ArrayList<>(qQuestionList());
        numOfQues_lbl.setText(""+qQuestionList.size());
        totalMark_lbl.setText(qQuestionList.size()+".00");
        for (int i = 0; i < qQuestionList.size(); i++) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/resources/Fxml/GUI62aItem.fxml"));
            try {
                HBox hBox = loader.load();
                GUI62aItemController itemController62 = loader.getController();
                itemController62.setData(qQuestionList.get(i));
                listQues.getChildren().add(hBox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void getTitle(String quizName) {
        nameData = quizName;
        title_lbl.setText("Editting quiz: " + quizName);
        title2_lbl.setText(quizName);
    }
    public void insertQuesToQuiz(Vector<String> quesList){

        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            for (int i = 0; i < quesList.size(); i++) {
                statement.executeUpdate("INSERT INTO ques_quiz(question_id, quiz_id) VALUES (" + quesList.get(i) + ", " + idData + ")");
            }
            System.out.println(quesList.size()+" question added to quiz "+ idData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        getTitle(nameData);
        getQuestionList(idData);
        home_btn.setOnAction(event -> showGUI11());
        quiz_btn.setOnAction(event -> showGUI61());
        fromQuesBank_mit.setOnAction(event -> showGUI63a());
        aRandomQues_mit.setOnAction(event -> showGUI65());
    }
}
