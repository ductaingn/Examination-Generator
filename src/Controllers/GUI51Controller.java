package Controllers;

import Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;

public class GUI51Controller implements Initializable {
    @FXML
    private Button cancel_btn;
    @FXML
    private Button create_btn;
    @FXML
    private Label switch_lbl;
    @FXML
    private ComboBox<Integer> clsDay_comb;
    @FXML
    private ComboBox<Integer> clsHour_comb;
    @FXML
    private ComboBox<Integer> clsMin_comb;
    @FXML
    private ComboBox<String> clsMonth_comb;
    @FXML
    private ComboBox<Integer> clsYear_comb;
    @FXML
    private TextArea description_fld;
    @FXML
    private TextField name_fld;
    @FXML
    private ComboBox<Integer> opnMin_comb;
    @FXML
    private ComboBox<Integer> opnDay_comb;
    @FXML
    private ComboBox<Integer> opnHour_comb;
    @FXML
    private ComboBox<String> opnMonth_comb;
    @FXML
    private ComboBox<Integer> opnYear_comb;
    @FXML
    private TextField timeLimit_fld;

    public void showGUI11() {
        Stage stage = (Stage)switch_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showGUI11();
    }

//    generate array number from 1 to k
    private Integer[] generator(int k) {
        Integer[] result = new Integer[k];
        for (int i = 0; i < k; i++) {
            result[i] = i + 1;
        }
        return result;
    }
    private final String[] month = {"January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October", "November", "December"};
    public Connection getConnection() {
        Connection connection;
        try {
//            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public enum MyMonth {January, February, March, April, May,
            June, July, August, September, October, November, December};
    public void insertQuiz() {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            int minutes = Integer.parseInt(timeLimit_fld.getText());
            int monthOpn = MyMonth.valueOf(opnMonth_comb.getValue()).ordinal() + 1;
            int monthCls = MyMonth.valueOf(clsMonth_comb.getValue()).ordinal() + 1;
            statement.executeUpdate("insert into quiz (name, description, timeOpen, timeClose, timeLimit) " +
                    "values ('" + name_fld.getText() + "', '" + description_fld.getText() + "', '" +
                    opnYear_comb.getValue() + "-" + monthOpn + "-" + opnDay_comb.getValue() +
                    " " + opnHour_comb.getValue() + ":" + opnMin_comb.getValue() + "', '" +
                    clsYear_comb.getValue() + "-" + monthCls + "-" + clsDay_comb.getValue() +
                    " " + clsHour_comb.getValue() + ":" + clsMin_comb.getValue() + "', '" +
                    minutes/60 + ":" + minutes%60 + "');");
            System.out.println("Quiz Created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cancel_btn.setOnAction(event -> showGUI11());
        create_btn.setOnAction(event -> insertQuiz());


        opnDay_comb.getItems().addAll(generator(31));
        clsDay_comb.getItems().addAll(generator(31));
        opnMonth_comb.getItems().addAll(month);
        clsMonth_comb.getItems().addAll(month);
        opnYear_comb.getItems().addAll(2022, 2023, 2024, 2025);
        clsYear_comb.getItems().addAll(2022, 2023, 2024, 2025);
        opnHour_comb.getItems().addAll(generator(23));
        clsHour_comb.getItems().addAll(generator(23));
        opnMin_comb.getItems().addAll(generator(60));
        clsMin_comb.getItems().addAll(generator(60));
    }
}
