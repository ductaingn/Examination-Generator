package Controllers;

import Models.QQuestion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class GUI62aItemController implements Initializable {
    @FXML
    private Button delete_btn;
    @FXML
    private Label rank_lbl;
    @FXML
    private Label name_lb;
    @FXML
    private Label text_lbl;
    @FXML
    private Label quesID_lbl;
    public static int rank = 0;
    public static String quiz_id_data;
    public void setData(QQuestion qQuestion){
        rank_lbl.setText(rank+1 +"");
        name_lb.setText(qQuestion.getName());
        text_lbl.setText(qQuestion.getText());
        quesID_lbl.setText(qQuestion.getQuestion_id()+"");
    }
    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void deleteQuesFromQuiz(String ques_id){
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
//            TODO
//            query này cần update vì trong question sẽ có các choice,
//            việc xóa question yêu cầu cần xóa các choice của question ấy trước
            statement.executeUpdate("DELETE FROM ques_quiz WHERE question_id = " + ques_id + " and quiz_id = " + quiz_id_data + ";");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String ques_id;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        delete_btn.setOnAction(event -> {
            ques_id = quesID_lbl.getText();
            deleteQuesFromQuiz(ques_id);
            System.out.println("Delete question " + ques_id + " from quiz " + quiz_id_data);
        });
    }
}
