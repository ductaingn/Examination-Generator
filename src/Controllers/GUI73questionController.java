package Controllers;

import Models.Choice;
import Models.QQuestion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GUI73questionController extends GUI73choiceController {
    @FXML
    private Text isAnswer_lbl;
    @FXML
    private Label question_text_lbl;
    @FXML
    private Label questionNo_lbl;
    @FXML
    private VBox choice_layout;
    public static String idData;
    public static int quesRank = 0, choiceRank = 0, quesId;
    public void setQuesData(QQuestion qQuestion) {
        questionNo_lbl.setText(quesRank+1 + "");
        question_text_lbl.setText(qQuestion.getText());
    }
    private List<Choice> choiceList() {
        Connection connection = getConnection();
        String query = "SELECT content FROM choice " +
                "WHERE question_id = " + quesId;
        List<Choice> list = new ArrayList<>();
        Choice choice;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                choice = new Choice();
                choice.setChoiceText(resultSet.getString("content"));
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
        for(choiceRank = 0; choiceRank < choiceList.size(); choiceRank++){
            FXMLLoader loader= new FXMLLoader();
            loader.setLocation(getClass().getResource("/resources/Fxml/GUI73choice.fxml"));
            try {
                HBox hBox = loader.load();
                GUI73choiceController controller = loader.getController();
                controller.setChoiceData(choiceList.get(choiceRank));
                choice_layout.getChildren().add(hBox);
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }
    }
}
