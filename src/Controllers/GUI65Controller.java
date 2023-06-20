package Controllers;

import Models.Model;
import Models.QQuestion;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GUI65Controller implements Initializable {
    @FXML
    private Pagination pagination;
    @FXML
    private Label switch_lbl;
    @FXML
    private Button close_btn;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private ComboBox comboBox1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }
}
