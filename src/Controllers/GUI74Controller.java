package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Models.QQuestion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class GUI74Controller extends GUI73Controller implements Initializable {
	@FXML
	private Button finishReview_btn;
	@FXML
    private Label completed_on;
    @FXML
    private Label grade;
    @FXML
    private Button home_btn;
    @FXML
    private Label marks;
    @FXML
    private Label started_on;
    @FXML
    private Label switch_lbl;
    @FXML
    private Label timeTaken;
    @FXML
    private Label title2_lbl;
	public void getQuestionList(String quizID){
		question_layout.getChildren().clear();
		idData = quizID;
		List<QQuestion> qQuestionList = new ArrayList<>(qQuestionList());
		for (quesRank = 0; quesRank < qQuestionList().size(); quesRank++){
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/resources/Fxml/GUI73question.fxml"));
			try {
				HBox hBox = loader.load();
				GUI73questionController controller = loader.getController();
				controller.setQuesDataAndAnswer(qQuestionList.get(quesRank));
				controller.getChoiceListAnswer(qQuestionList.get(quesRank).getQuestion_id());
				controller.setStatusAnswer(isSelected[quesRank]);
				question_layout.getChildren().add(hBox);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getQuestionList(idData);
		showNavi();
		finishReview_btn.setOnAction(event -> {
			myAnswer.clear();
			myChoice.clear();
			showGUI11();
		});
		{
			title2_lbl.setText(nameData);
			marks.setText(diem + ".00/" + qQuestionList().size() + ".00");
			grade.setText((float) Math.round((float) diem / qQuestionList().size() * 1000) / 100 + " out of 10.00 (" +(float) Math.round((float) diem /qQuestionList().size() * 10000) / 100 + "%)");
			int seconds, hours, minutes;
			if (timeTaken1 != null) {
				seconds = timeTaken1;
				hours = seconds / 3600;
				seconds = seconds % 3600;
				minutes = seconds / 60;
				seconds = seconds % 60;
				timeTaken.setText(hours + " hour " + minutes + " min " + seconds + "secs");

				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
				DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy, h:mm a", Locale.ENGLISH);
				String dateTimeString = now.format(formatter);
				LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
				String formattedDateTime = dateTime.format(outputFormatter);

				completed_on.setText(formattedDateTime);
				System.out.println(formattedDateTime);

				LocalDateTime dateTime1 = LocalDateTime.parse(startedTime1, formatter);
				String formattedDateTime1 = dateTime1.format(outputFormatter);
				started_on.setText(formattedDateTime1);
				System.out.println(formattedDateTime1);
			}
		}
	}
}
