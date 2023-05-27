import Models.Model;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        Model.getInstance().getViewFactory().showGUI11();
    }
}