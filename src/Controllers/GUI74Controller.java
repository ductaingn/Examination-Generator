package Controllers;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import Controllers.GUI72Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GUI74Controller extends GUI73Controller implements Initializable {
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
    private Label state;
    @FXML
    private Label switch_lbl;
    @FXML
    private Label timeTaken;
    @FXML
    private Label title2_lbl;
    public String beginTime;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		int seconds  = 0;
		int hours = 0;
		int minutes = 0;
		if(timeTaken1 != null) {
			seconds = timeTaken1.intValue();
			hours = seconds/3600;
			seconds = seconds % 3600;
			minutes = seconds/60;
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
