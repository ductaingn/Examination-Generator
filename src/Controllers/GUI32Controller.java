package Controllers;

import Models.QQuestion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.Vector;
import Models.Model;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class GUI32Controller implements Initializable {
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    @FXML
    private ScrollPane scrollPane;
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

    public void preloadQuestion(Integer questionId){
        try{
            FXMLLoader fxmlLoader= new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/resources/Fxml/GUI32pane.fxml"));
            scrollPane.setContent(fxmlLoader.load());
            location=fxmlLoader.getLocation();
            resources=fxmlLoader.getResources();

            GUI32paneController gui32paneController=fxmlLoader.getController();
            QQuestion question = new QQuestion();

            String categoryName = "";
            String questionName = "";
            String questionText = "";
            int questionMark=0;
            try{
                Connection connection = getConnection();
                String query = "SELECT * FROM question WHERE question_id = "+ questionId +" ;";
                ResultSet resultSet = connection.createStatement().executeQuery(query);
                resultSet.next();

                questionName=resultSet.getString("name");
                questionText=resultSet.getString("text");
                questionMark=resultSet.getInt("mark");
                query="SELECT * FROM category WHERE category_id = "+ resultSet.getInt("category_id")+" ;";
                resultSet=connection.createStatement().executeQuery(query);
                resultSet.next();
                categoryName=resultSet.getString("name");

                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }

            gui32paneController.getSaveChanges_btn().setOnAction(event -> gui32paneController.alterQuestion(questionId));
            gui32paneController.setCategory(categoryName);
            gui32paneController.setQuestionNameTextField(questionName);
            gui32paneController.setQuestionTextTextArea(questionText);
            gui32paneController.setQuestionMarkTextField(questionMark);

            //Load Choices
            Vector<GUI32ChoiceController> choicesControllers = gui32paneController.getChoicesControllers();
            try {
                Connection connection = getConnection();
                Statement statement=connection.createStatement();
                ResultSet choiceSet = statement.executeQuery("SELECT * FROM test.choice WHERE question_id = '" + questionId + "';");

                for(int i=0;i<choicesControllers.size();i++){
                    choiceSet.next();
                    choicesControllers.get(i).setTextArea(choiceSet.getString("content"));
                    choicesControllers.get(i).setGradeComboBox(choiceSet.getDouble("grade"));
                    InputStream imageInputStream = choiceSet.getBinaryStream("image");
                    if(imageInputStream!=null){
                        Image image = new Image(imageInputStream);
                        choicesControllers.get(i).setImageView(image);
                    }
                }
                while(choiceSet.next()){
                    int controllerIndex=choicesControllers.size();
                    gui32paneController.insertKMoreChoices(1);
                    choicesControllers.get(controllerIndex).setTextArea(choiceSet.getString("content"));
                    choicesControllers.get(controllerIndex).setGradeComboBox(choiceSet.getDouble("grade"));
                    InputStream imageInputStream = choiceSet.getBinaryStream("image");
                    if(imageInputStream!=null){
                        Image image = new Image(imageInputStream);
                        choicesControllers.get(controllerIndex).setImageView(image);
                    }
                    controllerIndex=controllerIndex+1;
                }
                statement.close();
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private Button home_btn;
    @FXML
    private Button gui21_btn;
    @FXML
    private Label switch_lbl;

    public void showGUI11() {
        Stage stage = (Stage)switch_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showGUI11();
    }
    public void showGUI21() {
        Stage stage = (Stage)switch_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showGUI21();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        home_btn.setOnAction(event -> showGUI11());
        gui21_btn.setOnAction(event -> showGUI21());
    }
}
