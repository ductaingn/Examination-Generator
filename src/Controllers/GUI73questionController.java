package Controllers;

import Models.Choice;
import Models.QQuestion;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUI73questionController {
    GUI73Controller parent;
    public void getInfo(GUI73Controller parent) {
        this.parent=parent;
    }
    @FXML
    public VBox correctChoice_vbox;
    @FXML
    private Text isAnswer_lbl;
    @FXML
    private Label question_text_lbl;
    @FXML
    public Label questionNo_lbl;
    @FXML
    private VBox choice_layout;
    public static int diem;
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
    public static int quesRank = 0, choiceRank = 0, quesId, maxQues = 100;

    public static ArrayList<ArrayList<Character>> myAnswer = new ArrayList<>();
    public static ArrayList<ArrayList<Character>> myChoice = new ArrayList<>();

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
    public static boolean[] isSelected = new boolean[maxQues];
    public static int[] selected = new int[maxQues];

    public void setQuesData(QQuestion qQuestion) {
        questionNo_lbl.setText(quesRank+1 + "");
        question_text_lbl.setText(qQuestion.getText());
    }
    public void setQuesDataAndAnswer(QQuestion qQuestion) {
        questionNo_lbl.setText(quesRank+1 + "");
        question_text_lbl.setText(qQuestion.getText());

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/resources/Fxml/GUI74correctAnswer.fxml"));
        VBox vBox = new VBox();
        try {
            vBox = loader.load();
        } catch (IOException e){
            e.printStackTrace();
        }
        GUI74correctAnswerController controller = loader.getController();
        controller.setCorrectChoice_lbl(qQuestion);
        correctChoice_vbox.getChildren().add(vBox);
    }
    public void setStatus(boolean check) {
        if (check) isAnswer_lbl.setText("Answered");
        else isAnswer_lbl.setText("Not yet answered");
    }
    public void setStatusAnswer(boolean check) {
        if (check) isAnswer_lbl.setText("Answered");
        else isAnswer_lbl.setText("Not answered");
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
        choice_layout.getChildren().clear();
        quesId = quesID;
        List<Choice> choiceList = new ArrayList<>(choiceList());
        int dem = 0;
        //dem so dap an dung, if dem = 1 -> single choice else multiple choice
        myAnswer.add(new ArrayList<>());
        myChoice.add(new ArrayList<>());
        Arrays.fill(isSelected, false);
        Arrays.fill(selected, 0);
         for (choiceRank = 0; choiceRank < choiceList.size(); choiceRank++){
            if (choiceList.get(choiceRank).getChoiceGrade() > 0.0) {
                myAnswer.get(Integer.parseInt((questionNo_lbl.getText())) - 1).add(myAnswer.get(Integer.parseInt((questionNo_lbl.getText())) - 1).size(), (char)(97 + choiceRank));
                dem++;
            }
        }

        if (dem == 1) {
            ToggleGroup toggleGroup = new ToggleGroup();
            for (choiceRank = 0; choiceRank < choiceList.size(); choiceRank++) {
                RadioButton radioButton = new RadioButton((char)(97 + choiceRank) + ".  " + choiceList.get(choiceRank).getChoiceText());
                radioButton.setStyle("-fx-font-size: 16");
                toggleGroup.getToggles().add(radioButton);
                choice_layout.getChildren().add(radioButton);
            }
            toggleGroup.selectedToggleProperty().addListener(new ChangeListener<>() {
                @Override
                public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
                    RadioButton chk = (RadioButton) t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
                    if (myChoice.get(Integer.parseInt((questionNo_lbl.getText())) - 1).size() != 0)
                        myChoice.get(Integer.parseInt((questionNo_lbl.getText())) - 1).remove(0);
                    myChoice.get(Integer.parseInt((questionNo_lbl.getText())) - 1).add(0, chk.getText().charAt(0));
                    isSelected[Integer.parseInt(questionNo_lbl.getText())-1] = true;
                    setStatus(isSelected[Integer.parseInt(questionNo_lbl.getText())-1]);
                    parent.showNavi(qQuestionList().size());
                }
            });
        } else {
            for (choiceRank = 0; choiceRank < choiceList.size(); choiceRank++) {
                CheckBox checkBox = new CheckBox((char)(97 + choiceRank) + ".  " + choiceList.get(choiceRank).getChoiceText());
                checkBox.setStyle("-fx-font-size: 16");
                choice_layout.getChildren().add(checkBox);

                checkBox.selectedProperty().addListener(new ChangeListener<>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if(newValue){
                            myChoice.get(Integer.parseInt((questionNo_lbl.getText())) - 1).add(0, checkBox.getText().charAt(0));
                            selected[Integer.parseInt((questionNo_lbl.getText()))]++;
                        }else{
                            myChoice.get(Integer.parseInt((questionNo_lbl.getText())) - 1).remove(Character.valueOf(checkBox.getText().charAt(0)));
                            selected[Integer.parseInt((questionNo_lbl.getText()))]--;
                        }
                        isSelected[Integer.parseInt(questionNo_lbl.getText())-1] = selected[Integer.parseInt((questionNo_lbl.getText()))] != 0;
                        setStatus(isSelected[Integer.parseInt(questionNo_lbl.getText())-1]);
                        parent.showNavi(qQuestionList().size());
                    }
                });
            }
        }
    }
    public void getChoiceListAnswer(int quesID){
        choice_layout.getChildren().clear();
        quesId = quesID;
        List<Choice> choiceList = new ArrayList<>(choiceList());
        int dem = 0;
        for (choiceRank = 0; choiceRank < choiceList.size(); choiceRank++){
            if (choiceList.get(choiceRank).getChoiceGrade() > 0.0) {
                dem++;
            }
        }
        if (dem == 1) {
            for (choiceRank = 0; choiceRank < choiceList.size(); choiceRank++) {
                RadioButton radioButton = new RadioButton((char)(97 + choiceRank) + ".  " + choiceList.get(choiceRank).getChoiceText());
                radioButton.setStyle("-fx-font-size: 16");
                if (myChoice.get(Integer.parseInt((questionNo_lbl.getText())) - 1).size() > 0) {
                    if ((char) (97 + choiceRank) == myChoice.get(Integer.parseInt((questionNo_lbl.getText())) - 1).get(0)) {
                        radioButton.setSelected(true);
                    }
                }
                radioButton.setDisable(true);
                choice_layout.getChildren().add(radioButton);
            }
        } else {
            for (choiceRank = 0; choiceRank < choiceList.size(); choiceRank++) {
                CheckBox checkBox = new CheckBox((char)(97 + choiceRank) + ".  " + choiceList.get(choiceRank).getChoiceText());
                checkBox.setStyle("-fx-font-size: 16");
                if (myChoice.get(Integer.parseInt((questionNo_lbl.getText())) - 1).size() > 0) {
                    for (int i = 0; i < myChoice.get(Integer.parseInt((questionNo_lbl.getText())) - 1).size(); i++) {
                        if ((char) (97 + choiceRank) == myChoice.get(Integer.parseInt((questionNo_lbl.getText())) - 1).get(i)) {
                            checkBox.setSelected(true);
                        }
                    }
                }
                checkBox.setDisable(true);
                choice_layout.getChildren().add(checkBox);
            }
        }
        if (myChoice.get(Integer.parseInt((questionNo_lbl.getText())) - 1).equals(myAnswer.get(Integer.parseInt((questionNo_lbl.getText())) - 1))
            && myAnswer.get(Integer.parseInt((questionNo_lbl.getText())) - 1).size() > 0) {
            diem++;
        }
    }
}