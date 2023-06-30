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
                RadioButton radioButton = new RadioButton(choiceList.get(choiceRank).getChoiceText());
                radioButton.setStyle("-fx-font-size: 16");
                toggleGroup.getToggles().add(radioButton);
                choice_layout.getChildren().add(radioButton);
            }
            toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                @Override
                public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
                    RadioButton chk = (RadioButton) t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
                    System.out.println("da chon " + chk.getText());
                }
            });
        } else {
            for (choiceRank = 0; choiceRank < choiceList.size(); choiceRank++) {
                CheckBox checkBox = new CheckBox(choiceList.get(choiceRank).getChoiceText());
                checkBox.setStyle("-fx-font-size: 16");
                choice_layout.getChildren().add(checkBox);

                checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        // TODO Auto-generated method stub
                        if(newValue){
                            System.out.println("da chon " + checkBox.getText());
                            // them vao vector
                        }else{
                            System.out.println("da huy chon " + checkBox.getText());
                            // xoa khoi vector
                        }
                    }
                });
            }
        }
    }

}
