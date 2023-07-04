package Controllers;

import Models.Choice;
import Models.QQuestion;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GUI74correctAnswerController {
    @FXML
    private Label correctChoice_lbl;
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
    private List<Choice> correctChoiceList(int quesId) {
        Connection connection = getConnection();
        String query = "SELECT content, grade FROM choice " +
                "WHERE grade > 0 AND question_id = " + quesId;
        List<Choice> list = new ArrayList<>();
        Choice choice;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                choice = new Choice();
                choice.setChoiceText(resultSet.getString("content"));
                choice.setChoiceGrade(resultSet.getDouble("grade"));
                list.add(choice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public void setCorrectChoice_lbl(QQuestion qQuestion) {
        List<Choice> correctChoiceList = new ArrayList<>(correctChoiceList(qQuestion.getQuestion_id()));
        String string = null;
        if (correctChoiceList.size() > 0) 
            string = "The correct answer is: " + correctChoiceList.get(0).getChoiceText();
        for (int i = 1; i < correctChoiceList.size(); i++) {
            string += ", " + correctChoiceList.get(i).getChoiceText();
        }
        correctChoice_lbl.setText(string);
    }
}
