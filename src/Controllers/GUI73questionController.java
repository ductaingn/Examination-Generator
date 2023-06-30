package Controllers;

import Models.Choice;
import Models.QQuestion;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GUI73questionController {
    GUI73Controller parent;
    public void getInfo(GUI73Controller parent) {
        this.parent=parent;
    }
    @FXML
    private Text isAnswer_lbl;
    @FXML
    private Label question_text_lbl;
    @FXML
    private Label questionNo_lbl;
    @FXML
    private VBox choice_layout;
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
    public static String idData;
    public static int quesRank = 0, choiceRank = 0, quesId;

    public List<QQuestion> qQuestionList() {
        Connection connection = getConnection();
        String query = "SELECT text, q.question_id FROM question q, ques_quiz qq " +
                "WHERE q.question_id = qq.question_id AND qq.quiz_id = " + idData;
        List<QQuestion> list = new ArrayList<>();
        QQuestion qQuestion;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                qQuestion = new QQuestion();
                qQuestion.setText(resultSet.getString("text"));
                qQuestion.setQuestion_id(resultSet.getInt("question_id"));
                list.add(qQuestion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean[] isSelected = new boolean[14];

    public void setQuesData(QQuestion qQuestion) {
        questionNo_lbl.setText(quesRank+1 + "");
        question_text_lbl.setText(qQuestion.getText());
    }
    private List<Choice> choiceList() {
        Connection connection = getConnection();
        String query = "SELECT content, grade FROM choice " +
                "WHERE question_id = " + quesId;
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
    public void getChoiceList(int quesID){
        // TODO: doi mau navi
        choice_layout.getChildren().clear();
        quesId = quesID;
        List<Choice> choiceList = new ArrayList<>(choiceList());

        int dem = 0;
        for (choiceRank = 0; choiceRank < choiceList.size(); choiceRank++){
            if (choiceList.get(choiceRank).getChoiceGrade() > 0.0) dem++;
        }

        if (dem == 1) {
            ToggleGroup toggleGroup = new ToggleGroup();
            for (choiceRank = 0; choiceRank < choiceList.size(); choiceRank++) {
                RadioButton radioButton = new RadioButton((char)(97 + choiceRank) + ".  " + choiceList.get(choiceRank).getChoiceText());
                radioButton.setStyle("-fx-font-size: 16");
                toggleGroup.getToggles().add(radioButton);
                choice_layout.getChildren().add(radioButton);
                //TODO deselected radioButton and set isSelected to false
            }
            toggleGroup.selectedToggleProperty().addListener(new ChangeListener<>() {
                @Override
                public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
                    RadioButton chk = (RadioButton) t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
                    System.out.println(questionNo_lbl.getText() + ". da chon " + chk.getText());
                    isSelected[Integer.parseInt(questionNo_lbl.getText())-1] = true;
                    parent.showNavi(qQuestionList().size());
                }
            });
        } else {
            for (choiceRank = 0; choiceRank < choiceList.size(); choiceRank++) {
                CheckBox checkBox = new CheckBox((char)(97 + choiceRank) + ".  " + choiceList.get(choiceRank).getChoiceText());
                checkBox.setStyle("-fx-font-size: 16");
                choice_layout.getChildren().add(checkBox);

                checkBox.selectedProperty().addListener(new ChangeListener<>() {
                    static int selected = 0;
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                        if(newValue){
                            selected++;
                            System.out.println(questionNo_lbl.getText() + ". da chon " + checkBox.getText()+" "+ selected);
                        }else{
                            selected--;
                            System.out.println(questionNo_lbl.getText() + ". da huy chon " + checkBox.getText()+" "+ selected);
                        }
                        isSelected[Integer.parseInt(questionNo_lbl.getText())-1] = selected != 0;
                        parent.showNavi(qQuestionList().size());
                    }
                });
            }
        }
    }
}