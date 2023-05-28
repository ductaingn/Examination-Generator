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
    private ComboBox<String> timeLimit_comb;
    @FXML
    private TextField timeLimit_fld;
    int[] dayInMonth = new int[31];

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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cancel_btn.setOnAction(event -> showGUI11());
        create_btn.setOnAction(event -> showGUI11());

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
        timeLimit_comb.getItems().addAll("minutes", "hours");
    }
}
