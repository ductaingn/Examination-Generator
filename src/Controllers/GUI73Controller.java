package Controllers;

import Models.Model;
import javafx.animation.KeyFrame;
import Models.QQuestion;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GUI73Controller extends GUI73questionController implements Initializable {
    @FXML
    private Button quiz_btn;
    @FXML
    private VBox quizNavigation_vbox;
    @FXML
    private Button home_btn;
    @FXML
    private VBox question_layout;
    @FXML
    private Label switch_lbl;
    @FXML
    private Label title2_lbl;
    @FXML
    private Label time_lbl;
    @FXML
    private Button finishAttempt_btn;
    public static String nameData, timeData;
    public void getInfo(String quiz_id, String quiz_name, String quiz_time) {
        idData = quiz_id;
        nameData = quiz_name;
        timeData = quiz_time;
    }
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
    private List<QQuestion> qQuestionList() {
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
    public void getQuestionList(String quizID){
        question_layout.getChildren().clear();
        idData = quizID;
        List<QQuestion> qQuestionList = new ArrayList<>(qQuestionList());
        for(quesRank = 0; quesRank < qQuestionList.size(); quesRank++){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/resources/Fxml/GUI73question.fxml"));
            try {
                HBox hBox = loader.load();
                GUI73questionController controller = loader.getController();
                controller.setQuesData(qQuestionList.get(quesRank));
                controller.getChoiceList(qQuestionList.get(quesRank).getQuestion_id());

                question_layout.getChildren().add(hBox);
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }
    }

    public void showNavi(int k){
        System.out.println(k);
        int dem = 0;
        while (dem*5 <= k) {
            HBox hBox = new HBox(5);
            for (int i = 1; i <= 5 && dem*5 + i <= k; i++){
                FXMLLoader loader= new FXMLLoader();
                loader.setLocation(getClass().getResource("/resources/Fxml/GUI73itemNavi.fxml"));
                try {
                    VBox vBox = loader.load();
                    GUI73itemNaviController controller = loader.getController();
                    controller.setQuesNo_navi_lbl(dem*5+i);
                    hBox.getChildren().add(vBox);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            quizNavigation_vbox.getChildren().add(hBox);
            dem++;
        }
    }

    public Integer starttime;
	public Integer count;
	public Integer hour;
	public Integer seconds;
	public Integer minute;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showNavi(qQuestionList().size());
        getQuestionList(idData);
        title2_lbl.setText(nameData);
        time_lbl.setText(timeData);
        finishAttempt_btn.setOnAction(event -> System.out.println("finish attempt"));
      
        home_btn.setOnAction(event -> showGUI11());
        quiz_btn.setOnAction(event -> showGUI61());

        System.out.println(timeData);
        {
            if (timeData!=null) {
                starttime = Integer.parseInt(timeData);
                count = starttime * 60;
                hour = starttime / 60;
                seconds = 0;
                minute = starttime % 60;
                if (seconds < 10) {
                    if (minute < 10) {
                        time_lbl.setText(hour + ":0" + minute + ":0" + seconds);
                    } else {
                        time_lbl.setText(hour + ":" + minute + ":0" + seconds);
                    }
                } else {
                    if (minute < 10) {
                        time_lbl.setText(hour + ":0" + minute + ":" + seconds.toString());
                    } else {
                        time_lbl.setText(hour + ":" + minute + ":" + seconds.toString());
                    }
                }
                doTime();
            }
        }
    }
    private void doTime() {
        Timeline time = new Timeline();
        KeyFrame frame = new KeyFrame(Duration.seconds(1), event -> {
            seconds--;
            count--;
            if (seconds <= 0) {
                minute--;
                seconds = 60;
            }
            if (minute == 0 && hour > 0) {
                hour--;
            }
            if (seconds < 10) {
                if (minute < 10) {
                    time_lbl.setText(hour + ":0" + minute + ":0" + seconds);
                } else {
                    time_lbl.setText(hour + ":" + minute + ":0" + seconds);
                }
            } else {
                if (minute < 10) {
                    time_lbl.setText(hour + ":0" + minute + ":" + seconds);
                } else {
                    time_lbl.setText(hour + ":" + minute + ":" + seconds);
                }
            }
            if (count <= 0) {
                time_lbl.setText("Time Limit  ");
                time.stop();
                
            }
        });
        time.setCycleCount(Timeline.INDEFINITE);
        time.getKeyFrames().add(frame);
        if (time != null) {
            time.stop();
        }
        time.play();
    }
}


