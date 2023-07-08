package Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewFactory {
    public void showGUI11() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/GUI11.fxml"));
        createStage(loader);
    }
    public void showGUI21() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/GUI21.fxml"));
        createStage(loader);
    }
    public void showGUI32() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/GUI32.fxml"));
        createStage(loader);
    }
    public void showGUI51() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/GUI51.fxml"));
        createStage(loader);
    }
    public void showGUI61() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/GUI61.fxml"));
        createStage(loader);
    }
    public void showGUI62a() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/GUI62a.fxml"));
        createStage(loader);
    }
    public void showGUI63a() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/GUI63a.fxml"));
        createStage(loader);
    }
    public void showGUI65() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/GUI65.fxml"));
        createStage(loader);
    }
    public void showGUI72() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/GUI72.fxml"));
        createStage(loader);
    }
    public void showGUI73() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/GUI73.fxml"));
        createFullScreenStage(loader);
    }
    public void showGUI74() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/GUI74.fxml"));
        createFullScreenStage(loader);
    }
    public void createStage(FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    public void createFullScreenStage(FXMLLoader loader){
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
    public void closeStage(Stage stage) {
        stage.close();
    }
}