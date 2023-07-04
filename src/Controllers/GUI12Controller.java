package Controllers;

import Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GUI12Controller implements Initializable {
    @FXML
    private Label categoryLabel;
    @FXML
    private Label exportLabel;
    @FXML
    private Label importLabel;
    @FXML
    private Label questionLabel;
    private Stage gui11Stage = new Stage();

    public void setGui11Stage(Stage gui11Stage) {
        this.gui11Stage = gui11Stage;
    }

    public void showGUI21questionTab(){
        Stage stage = (Stage)categoryLabel.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showGUI21();
        Model.getInstance().getViewFactory().closeStage(gui11Stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Stage gui12Stage=new Stage();
        questionLabel.setOnMouseClicked(event -> showGUI21questionTab());
    }
}