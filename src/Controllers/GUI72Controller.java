package Controllers;

import Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class GUI72Controller implements Initializable {
    @FXML
    private Button cancel_btn;
    @FXML
    private Button close_btn;
    @FXML
    private Button start_btn;
    @FXML
    private Button export_btn;
    @FXML
    private Label switch_lbl;
    @FXML
    private Label content_lbl;
    public static String idData, nameData, timeData;
    public void showGUI61() {
        Stage stage = (Stage)switch_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showGUI61();
    }
    public void showGUI73() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/GUI73.fxml"));
            Parent root = loader.load();
            GUI73Controller gui73Controller = loader.getController();
            gui73Controller.getInfo(idData, nameData, timeData);

            Stage stage = (Stage)switch_lbl.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
            Model.getInstance().getViewFactory().showGUI73();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
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
    public void getInfo(String quiz_id, String quiz_name, String quiz_time) {
        idData = quiz_id;
        nameData = quiz_name;
        timeData = quiz_time;
    }
    public void getPdf(){
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "select qs.question_id, qs.text, c.content " +
                    "from question qs join choice c on qs.question_id = c.question_id " +
                    "join ques_quiz qq on qq.question_id = qs.question_id where qq.quiz_id = " + idData);
            ResultSet resultSet = preparedStatement.executeQuery();
            int currentId = 0; int count = 0;
            while (resultSet.next()) {
                if (resultSet.getInt("question_id") == currentId) {
                    System.out.println((char)(98+count) + ".  " + resultSet.getString("content"));
                    count++;
                } else {
                    System.out.println(resultSet.getString("text"));
                    System.out.println("a.  " + resultSet.getString("content"));
                    currentId = resultSet.getInt("question_id");
                    count = 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        content_lbl.setText("Your attempt will have a time of limit of " + timeData + " minutes. " +
                "When you start, the timer will begin to count down and cannot be paused. " +
                "You must finish your attempt before it expires. Are you sure you wish to start now?");
        cancel_btn.setOnAction(event -> showGUI61());
        close_btn.setOnAction(event -> showGUI61());
        start_btn.setOnAction(event -> showGUI73());
        export_btn.setOnAction(event -> {
            showGUI61();
            getPdf();
        });
    }
}