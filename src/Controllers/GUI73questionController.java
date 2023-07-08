package Controllers;

import Models.Choice;
import Models.QQuestion;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.*;
import java.util.*;

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
    public static int quesRank = 0, choiceRank = 0, quesId, maxQues = 100, diem;
    public static ArrayList<ArrayList<Character>> myAnswer = new ArrayList<>();
    public static ArrayList<ArrayList<Character>> myChoice = new ArrayList<>();
    public static ArrayList<ArrayList<Choice>> choiceList = new ArrayList<>();
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
        System.out.println(qQuestion.getText()); //TODO pdf
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
    public boolean shuffleCheck() {
        Connection connection = getConnection();
        String query = "SELECT isShuffle FROM quiz WHERE quiz_id = " + idData;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                if (resultSet.getBoolean("isShuffle")) return true;
                    else return false;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public void getchoiceList() {
//        lay cac choice tu database
        choiceList.clear();
        myAnswer.clear();
        myChoice.clear();
        Connection connection = getConnection();
        String query = "SELECT choice.content, choice.grade, question.question_id " +
                "FROM choice, question, ques_quiz " +
                "WHERE choice.question_id = question.question_id AND " +
                "question.question_id = ques_quiz.question_id AND ques_quiz.quiz_id = " + idData +
                " order by question_id;";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int currentQuesID, numOfQues = 0;
            {
                resultSet.next();
                Choice choice = new Choice();
                choice.setChoiceText(resultSet.getString("content"));
                choice.setChoiceGrade(resultSet.getDouble("grade"));
                currentQuesID = resultSet.getInt("question_id");
                choiceList.add(new ArrayList<>());
                choiceList.get(numOfQues).add(choice);
            }
            while (resultSet.next()) {
                Choice choice = new Choice();
                choice.setChoiceText(resultSet.getString("content"));
                choice.setChoiceGrade(resultSet.getDouble("grade"));
                if (resultSet.getInt("question_id") == currentQuesID) {
                    choiceList.get(numOfQues).add(choice);
                } else {
                    currentQuesID = resultSet.getInt("question_id");
                    numOfQues++;
                    choiceList.add(new ArrayList<>());
                    choiceList.get(numOfQues).add(choice);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < choiceList.size(); i++) {
            if (shuffleCheck()) Collections.shuffle(choiceList.get(i));
        }
    }
    public void setChoiceList(int i){
        choice_layout.getChildren().clear();
        //dem so dap an dung, if dem = 1 -> single choice else multiple choice
        myAnswer.add(new ArrayList<>());
        myChoice.add(new ArrayList<>());
        Arrays.fill(isSelected, false);
        Arrays.fill(selected, 0);
        int countCorrectAnswer = 0;
        for (int j = 0; j < choiceList.get(i).size(); j++){     //duyet tung dap an trong 1 cau hoi
            if (choiceList.get(i).get(j).getChoiceGrade() > 0){
                myAnswer.get(i).add((char)(97+j));
                countCorrectAnswer++;
            }
        }
        if (countCorrectAnswer == 1) {
            ToggleGroup toggleGroup = new ToggleGroup();
            for (int j = 0; j < choiceList.get(i).size(); j++) {
                RadioButton radioButton = new RadioButton((char)(97 + j) + ".  " + choiceList.get(i).get(j).getChoiceText());
                radioButton.setWrapText(true);
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
            for (int j = 0; j < choiceList.get(i).size(); j++) {
                CheckBox checkBox = new CheckBox((char)(97 + j) + ".  " + choiceList.get(i).get(j).getChoiceText());
                checkBox.setWrapText(true);
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
    public Image trueImage = new Image("/resources/Image/true.png");
    public Image falseImage = new Image("/resources/Image/false.png");
    public ImageView getTrueImage(){
        ImageView trueIv = new ImageView();
        trueIv.setImage(trueImage);
        trueIv.setFitHeight(16);
        trueIv.setFitWidth(16);
        trueIv.setSmooth(true);
        trueIv.setPreserveRatio(true);
        trueIv.setCache(true);
        return trueIv;
    }
    public ImageView getFalseImage(){
        ImageView falseIv = new ImageView();
        falseIv.setImage(falseImage);
        falseIv.setFitHeight(14);
        falseIv.setFitWidth(14);
        falseIv.setSmooth(true);
        falseIv.setPreserveRatio(true);
        falseIv.setCache(true);
        return falseIv;
    }

    public void setChoiceListAnswer(int i){
        choice_layout.getChildren().clear();
        int countCorrectAnswer = 0;
        for (int j = 0; j < choiceList.get(i).size(); j++){
            if (choiceList.get(i).get(j).getChoiceGrade() > 0.0) {
                countCorrectAnswer++;
            }
        }
        if (countCorrectAnswer == 1) {
            for (int j = 0; j < choiceList.get(i).size(); j++) {
                HBox hBox = new HBox(5);
                hBox.setAlignment(Pos.CENTER_LEFT);
                RadioButton radioButton = new RadioButton((char)(97 + j) + ".  " + choiceList.get(i).get(j).getChoiceText());
                System.out.println((char)(97 + j) + ".  " + choiceList.get(i).get(j).getChoiceText()); //TODO pdf
                radioButton.setStyle("-fx-font-size: 16");
                radioButton.setWrapText(true);
                hBox.getChildren().add(radioButton);
                if (myChoice.get(Integer.parseInt((questionNo_lbl.getText())) - 1).size() > 0) {
                    if ((char) (97 + j) == myChoice.get(Integer.parseInt((questionNo_lbl.getText())) - 1).get(0)) {
                        radioButton.setSelected(true);
                        if (myChoice.get(Integer.parseInt((questionNo_lbl.getText())) - 1).equals(myAnswer.get(Integer.parseInt((questionNo_lbl.getText())) - 1))
                                && myAnswer.get(Integer.parseInt((questionNo_lbl.getText())) - 1).size() > 0) {
                            diem++;
                            hBox.getChildren().add(getTrueImage());
                        } else {
                            hBox.getChildren().add(getFalseImage());
                        }
                    }
                }
                radioButton.setDisable(true);
                choice_layout.getChildren().add(hBox);
            }
        } else {
            for (int j = 0; j < choiceList.get(i).size(); j++) {
                HBox hBox = new HBox(5);
                hBox.setAlignment(Pos.CENTER_LEFT);
                CheckBox checkBox = new CheckBox((char)(97 + j) + ".  " + choiceList.get(i).get(j).getChoiceText());
                System.out.println((char)(97 + j) + ".  " + choiceList.get(i).get(j).getChoiceText()); //TODO pdf
                checkBox.setWrapText(true);
                hBox.getChildren().add(checkBox);
                checkBox.setStyle("-fx-font-size: 16");
                if (myChoice.get(Integer.parseInt((questionNo_lbl.getText())) - 1).size() > 0) {
                    for (int k = 0; k < myChoice.get(Integer.parseInt((questionNo_lbl.getText())) - 1).size(); k++) {
                        if ((char) (97 + j) == myChoice.get(Integer.parseInt((questionNo_lbl.getText())) - 1).get(k)) {
                            checkBox.setSelected(true);
                            if ((myAnswer.get(Integer.parseInt((questionNo_lbl.getText())) - 1).contains((char)(97+j)))) {
                                hBox.getChildren().add(getTrueImage());
                            } else {
                                hBox.getChildren().add(getFalseImage());
                            }
                        }
                    }
                }
                checkBox.setDisable(true);
                choice_layout.getChildren().add(hBox);
            }
            if (myChoice.get(Integer.parseInt((questionNo_lbl.getText())) - 1).equals(myAnswer.get(Integer.parseInt((questionNo_lbl.getText())) - 1))
                    && myAnswer.get(Integer.parseInt((questionNo_lbl.getText())) - 1).size() > 0) {
                diem++;
            }
        }
    }
}