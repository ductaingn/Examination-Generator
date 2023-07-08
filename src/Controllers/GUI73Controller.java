package Controllers;

import Models.Model;
import javafx.animation.KeyFrame;
import Models.QQuestion;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.util.*;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GUI73Controller extends GUI73questionController implements Initializable {
    @FXML
    private Button quiz_btn;
    @FXML
    private Button home_btn;
    @FXML
    public VBox question_layout;
    @FXML
    private Label switch_lbl;
    @FXML
    private Label title2_lbl;
    @FXML
    private Label time_lbl;
    @FXML
    private Button finishAttempt_btn;
    @FXML
    public VBox quizNavigation_vbox;
    private Integer count, hour, minute, seconds, timeTaken;
    private String startedTime;
    public static String nameData, timeData, startedTime1;
    public static Integer timeTaken1;
    public void getInfo(String quiz_id, String quiz_name, String quiz_time) {
        idData = quiz_id;
        nameData = quiz_name;
        timeData = quiz_time;
    }
    public void getTimeTaken(int time, String start) {
    	timeTaken1 = time;
    	startedTime1 = start;
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
                controller.getInfo(this);
                controller.setQuesData(qQuestionList.get(quesRank));
                controller.setChoiceList(quesRank);
                question_layout.getChildren().add(hBox);
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }
    }
    public void showNavi(int k){
        quizNavigation_vbox.getChildren().clear();
        int dem = 0;
        while (dem*5 <= k) {
            HBox hBox = new HBox(5);
            for (int i = 1; i <= 5 && dem*5 + i <= k; i++){
                FXMLLoader loader = new FXMLLoader();
                if (!isSelected[i+dem*5-1]) {
                    loader.setLocation(getClass().getResource("/resources/Fxml/GUI73itemNavi.fxml"));
                } else {
                    loader.setLocation(getClass().getResource("/resources/Fxml/GUI73itemNaviSelected.fxml"));
                }
                VBox vBox = new VBox();
                try {
                    vBox = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                GUI73itemNaviController controller = loader.getController();
                controller.setQuesNo_navi_lbl(dem * 5 + i);
                hBox.getChildren().add(vBox);
            }
            quizNavigation_vbox.getChildren().add(hBox);
            dem++;
        }
    }
    public void showNavi(){
        int dem = 0;
        while (dem*5 <= qQuestionList().size()) {
            HBox hBox = new HBox(5);
            for (int i = 1; i <= 5 && dem*5 + i <= qQuestionList().size(); i++){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/resources/Fxml/GUI73itemNaviSelected.fxml"));
                VBox vBox = new VBox();
                try {
                    vBox = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                GUI73itemNaviController controller = loader.getController();
                controller.setQuesNo_navi_lbl(dem * 5 + i);
                hBox.getChildren().add(vBox);
            }
            quizNavigation_vbox.getChildren().add(hBox);
            dem++;
        }
    }
  
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (idData != null) {
            getchoiceList();
            getQuestionList(idData);
            showNavi(qQuestionList().size());
        }
        LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        startedTime = now.format(formatter);
        title2_lbl.setText(nameData);
        time_lbl.setText("0:00:00");
        finishAttempt_btn.setOnAction(event -> {
        	Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText("Bạn có muốn kết thúc bài kiểm tra");
            ButtonType buttonTypeOne = new ButtonType("YES");
            ButtonType buttonTypeTwo = new ButtonType("NO");
            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

            // Chờ người dùng thực hiện hành động và lấy kết quả
            Optional<ButtonType> result = alert.showAndWait();

            // Xử lý sự kiện cho từng nút
            if (result.isPresent()) {
                if (result.get() == buttonTypeOne) {
                    // Xử lý sự kiện khi người dùng chọn Button One
                    for (int i = 0; i < qQuestionList().size(); i++) {
                        Collections.sort(myChoice.get(i));
                    }
                     getTimeTaken(timeTaken, startedTime);
                    Stage stage = (Stage)switch_lbl.getScene().getWindow();
                    Model.getInstance().getViewFactory().closeStage(stage);
                    Model.getInstance().getViewFactory().showGUI74();
                }
            }
        });
        home_btn.setOnAction(event -> showGUI11());
        quiz_btn.setOnAction(event -> showGUI61());
        {
            if (timeData!=null) {
                int startTime = Integer.parseInt(timeData);
                count = startTime * 60;
                hour = startTime / 60;
                seconds = 0;
                minute = startTime % 60;
                timeTaken = 0;
                if (minute < 10) {
                    time_lbl.setText(hour + ":0" + minute + ":0" + seconds);
                } else {
                    time_lbl.setText(hour + ":" + minute + ":0" + seconds);
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
            timeTaken++;
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/GUI74.fxml"));
                try {
					Parent root = loader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
                GUI73Controller gui74Controller = loader.getController();
                gui74Controller.getTimeTaken(timeTaken, startedTime);
                
                Stage stage = (Stage)switch_lbl.getScene().getWindow();
                Model.getInstance().getViewFactory().closeStage(stage);
                Model.getInstance().getViewFactory().showGUI74();
            }
        });
        time.setCycleCount(Timeline.INDEFINITE);
        time.getKeyFrames().add(frame);
        time.stop();
        time.play();
    }
}