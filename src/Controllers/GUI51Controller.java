package Controllers;

import Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;

public class GUI51Controller implements Initializable {
    @FXML
    private ComboBox timeLimit_comb;
    @FXML
    private CheckBox opn_ckb;
    @FXML
    private CheckBox cls_ckb;
    @FXML
    private CheckBox timeLimit_ckb;
    @FXML
    private Button delete_btn;
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
    private Integer[] generatorFrom1(int k) {
        Integer[] result = new Integer[k];
        for (int i = 0; i < k; i++) {
            result[i] = i + 1;
        }
        return result;
    }

    private Integer[] generatorFrom0(int k) {
        Integer[] result = new Integer[k];
        for (int i = 0; i < k; i++) {
            result[i] = i;
        }
        return result;
    }
    private final String[] month = {"January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October", "November", "December"};
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
    public enum MyMonth {January, February, March, April, May,
            June, July, August, September, October, November, December}
    public void insertQuiz() {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String monthOpn, monthCls;
            if (!opn_ckb.isSelected()) {
                monthOpn = null;
            } else {
                monthOpn = String.valueOf(MyMonth.valueOf(opnMonth_comb.getValue()).ordinal() + 1);
            }
            if (!cls_ckb.isSelected()){
                monthCls = null;
            } else {
                monthCls = String.valueOf(MyMonth.valueOf(clsMonth_comb.getValue()).ordinal() + 1);
            }
            statement.executeUpdate("insert into quiz (name, description, timeOpen, timeClose, timeLimit) " +
                    "values ('" + name_fld.getText() + "', '" + description_fld.getText() + "', '" +
                    opnYear_comb.getValue() + "-" + monthOpn + "-" + opnDay_comb.getValue() +
                    " " + opnHour_comb.getValue() + ":" + opnMin_comb.getValue() + "', '" +
                    clsYear_comb.getValue() + "-" + monthCls + "-" + clsDay_comb.getValue() +
                    " " + clsHour_comb.getValue() + ":" + clsMin_comb.getValue() + "', '" +
                    timeLimit_fld.getText() + "');");
            System.out.println("Quiz Created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteQuiz() {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM quiz WHERE name = '" + name_fld.getText() + "'");
            System.out.println("Quiz deleted");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cancel_btn.setOnAction(event -> showGUI11());
        create_btn.setOnAction(event -> insertQuiz());
        delete_btn.setOnAction(event -> deleteQuiz());
        {
            opnDay_comb.getItems().addAll(generatorFrom1(31));
            clsDay_comb.getItems().addAll(generatorFrom1(31));
            opnMonth_comb.getItems().addAll(month);
            clsMonth_comb.getItems().addAll(month);
            opnYear_comb.getItems().addAll(2022, 2023, 2024, 2025);
            clsYear_comb.getItems().addAll(2022, 2023, 2024, 2025);
            opnHour_comb.getItems().addAll(generatorFrom0(24));
            clsHour_comb.getItems().addAll(generatorFrom0(24));
            opnMin_comb.getItems().addAll(generatorFrom0(60));
            clsMin_comb.getItems().addAll(generatorFrom0(60));

            opnMin_comb.setDisable(true);
            opnHour_comb.setDisable(true);
            opnDay_comb.setDisable(true);
            opnMonth_comb.setDisable(true);
            opnYear_comb.setDisable(true);
            clsMin_comb.setDisable(true);
            clsHour_comb.setDisable(true);
            clsDay_comb.setDisable(true);
            clsMonth_comb.setDisable(true);
            clsYear_comb.setDisable(true);
            timeLimit_fld.setDisable(true);
            timeLimit_comb.setDisable(true);
        }
        opn_ckb.setOnAction(event -> {
            if (opn_ckb.isSelected()){
                System.out.println("Enable time open");
                opnMin_comb.setDisable(false);
                opnHour_comb.setDisable(false);
                opnDay_comb.setDisable(false);
                opnMonth_comb.setDisable(false);
                opnYear_comb.setDisable(false);
            } else {
                System.out.println("Disable time open");
                opnMin_comb.setDisable(true);
                opnHour_comb.setDisable(true);
                opnDay_comb.setDisable(true);
                opnMonth_comb.setDisable(true);
                opnYear_comb.setDisable(true);
            }
        });
        cls_ckb.setOnAction(event -> {
            if (cls_ckb.isSelected()){
                System.out.println("Enable time close");
                clsMin_comb.setDisable(false);
                clsHour_comb.setDisable(false);
                clsDay_comb.setDisable(false);
                clsMonth_comb.setDisable(false);
                clsYear_comb.setDisable(false);
            } else {
                System.out.println("Disable time close");
                clsMin_comb.setDisable(true);
                clsHour_comb.setDisable(true);
                clsDay_comb.setDisable(true);
                clsMonth_comb.setDisable(true);
                clsYear_comb.setDisable(true);
            }
        });
        timeLimit_ckb.setOnAction(event -> {
            if (timeLimit_ckb.isSelected()){
                System.out.println("Enable time limit");
                timeLimit_fld.setDisable(false);
                timeLimit_comb.setDisable(false);
            } else {
                System.out.println("Disable time limit");
                timeLimit_fld.setDisable(true);
                timeLimit_comb.setDisable(true);
            }
        });
    }
}
