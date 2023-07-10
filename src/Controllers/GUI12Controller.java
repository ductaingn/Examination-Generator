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
    private GUI21Controller gui21Controller;
    private Stage gui11Stage = new Stage();

    public void setGui11Stage(Stage gui11Stage) {
        this.gui11Stage = gui11Stage;
    }

    public void showGUI21(String tabName){
        Stage stage = (Stage)categoryLabel.getScene().getWindow();
        gui21Controller=Model.getInstance().getViewFactory().showGUI21(tabName);
        gui21Controller.setTabPane(tabName);
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().closeStage(gui11Stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Stage gui12Stage=new Stage();
        questionLabel.setOnMouseClicked(mouseEvent -> showGUI21(questionLabel.getText()));
        categoryLabel.setOnMouseClicked(mouseEvent -> showGUI21(categoryLabel.getText()));
        importLabel.setOnMouseClicked(mouseEvent -> showGUI21(importLabel.getText()));
        exportLabel.setOnMouseClicked(mouseEvent -> showGUI21(exportLabel.getText()));
    }
}