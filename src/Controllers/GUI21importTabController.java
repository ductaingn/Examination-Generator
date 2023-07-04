package Controllers;

import javafx.scene.input.DragEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Vector;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Button;
import Models.Model;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

public class GUI21importTabController implements Initializable {
    @FXML
    private ImageView image;
    @FXML 
    private Button button;
    @FXML 
    private Button import_btn;
 // Liên kết với cơ sở dữ liệu :))
    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //---------------
    class Question{
    	public String questionText;
    	public String questionAnswer;
    	public Vector<String> choiceList = new Vector<>();
    }

    public Vector<Question> question_List = new Vector<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Label label = new Label();
        // Xử lý sự kiện khi bắt đầu kéo
        image.setOnDragDetected(event -> {
            Dragboard dragboard = image.startDragAndDrop(TransferMode.COPY);
            dragboard.setDragView(image.snapshot(null, null));
            event.consume();
        });
        // Xử lý sự kiện khi có file được kéo vào
        image.setOnDragOver(event -> {
            if (event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });
        // Xử lý sự kiện khi file được thả vào
        image.setOnDragDropped((DragEvent event) -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;
            if (dragboard.hasFiles()) {
                String filePath = dragboard.getFiles().get(0).getAbsolutePath();
                label.setText("File path: " + filePath);
                try {
                    if (checkAndAddAikenStructure(filePath)) {
                    	Image img = new Image("resources//Image//file.png");
                    	image.setImage(img);
                        success = true;
                    }else {
                    	Image img = new Image("resources//Image//file.png");
                    	image.setImage(img);
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

            // Tạo một massageBox để thông báo File ó đúng cấu trúc Aiken hay không
            if (!success) {
            	import_btn.setOnAction(e->{
            		 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                     alert.setTitle("Thông báo");
                     alert.setHeaderText(null);
                     alert.setContentText("File không đúng cú pháp Aiken!");
                     alert.showAndWait();
                     
                     Stage stage = (Stage)import_btn.getScene().getWindow();
                     Model.getInstance().getViewFactory().closeStage(stage);
                     Model.getInstance().getViewFactory().showGUI21();
                     
            	});
            } else {
                import_btn.setOnAction(e->{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText(null);
                    alert.setContentText("Import File thành công!");
                    alert.showAndWait();
                   
                	Stage stage = (Stage)import_btn.getScene().getWindow();
                    Model.getInstance().getViewFactory().closeStage(stage);
                    Model.getInstance().getViewFactory().showGUI21();

                    try {
                        Connection connection = getConnection();
                        Statement statement = connection.createStatement();
                        for (int i = 0; i < question_List.size(); i++) {
                            statement.executeUpdate("INSERT INTO question(category_id, mark,name, text) VALUES (1, 1, '" + question_List.elementAt(i).questionText +"','" + question_List.elementAt(i).questionText + "');");
                            statement.executeUpdate("set @id = LAST_INSERT_ID();");
                            for (int j = 0; j < question_List.elementAt(i).choiceList.size(); j++) {
                                if (question_List.elementAt(i).choiceList.elementAt(j).charAt(0) == question_List.elementAt(i).questionAnswer.charAt(8))
                                    statement.executeUpdate("INSERT INTO choice(question_id, grade, content) VALUES(@id, 100, '" + question_List.elementAt(i).choiceList.elementAt(j).substring(3) +"');");
                                else
                                    statement.executeUpdate("INSERT INTO choice(question_id, grade, content) VALUES(@id, 0, '" + question_List.elementAt(i).choiceList.elementAt(j).substring(3) +"');");
                            }
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
           	    });
            }
            event.consume();
            event.setDropCompleted(success);
        });
 
        button.setOnAction(e -> {
        	String filePath = null;
        	FileChooser fileChooser = new FileChooser();

            // Đặt tiêu đề của cửa sổ FileChooser
            fileChooser.setTitle("Open File");

            // Hiển thị cửa sổ FileChooser và lấy đường dẫn đến file đã chọn
            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                filePath = selectedFile.getAbsolutePath();
            }
            try {
                if (checkAndAddAikenStructure(filePath)) {
                	Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText(null);
                    alert.setContentText("Import File thành công!");
                    alert.showAndWait();
                 
                	Stage stage = (Stage)button.getScene().getWindow();
                    Model.getInstance().getViewFactory().closeStage(stage);
                    Model.getInstance().getViewFactory().showGUI21();

                    try {
                        Connection connection = getConnection();
                        Statement statement = connection.createStatement();
                        for (int i = 0; i < question_List.size(); i++) {
                            statement.executeUpdate("INSERT INTO question(category_id, mark,name, text) VALUES (1, 1, '" + question_List.elementAt(i).questionText +"','" + question_List.elementAt(i).questionText + "');");
                            statement.executeUpdate("set @id = LAST_INSERT_ID();");
                            for (int j = 0; j < question_List.elementAt(i).choiceList.size(); j++) {
                                if (question_List.elementAt(i).choiceList.elementAt(j).charAt(0) == question_List.elementAt(i).questionAnswer.charAt(8))
                                    statement.executeUpdate("INSERT INTO choice(question_id, grade, content) VALUES(@id, 100, '" + question_List.elementAt(i).choiceList.elementAt(j).substring(3) +"');");
                                else
                                    statement.executeUpdate("INSERT INTO choice(question_id, grade, content) VALUES(@id, 0, '" + question_List.elementAt(i).choiceList.elementAt(j).substring(3) +"');");
                            }
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }else {
                	Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText(null);
                    alert.setContentText("File không đúng cú pháp Aiken!");
                    alert.showAndWait();
                    
                    Stage stage = (Stage)button.getScene().getWindow();
                    Model.getInstance().getViewFactory().closeStage(stage);
                    Model.getInstance().getViewFactory().showGUI21();
                }
            } catch (IOException m) {
                // TODO Auto-generated catch block
                m.printStackTrace();
            }
        });
    }
    // Check Aiken format
    public boolean checkAndAddAikenStructure(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line; // Các
        boolean isValid = true; // Bool check

        // Dùng để Check Choice format
        String regex = "[A-Z]\\.";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;
        // --------//
        
        Question qs = new Question();
        
        while ((line = reader.readLine()) != null) {
            if (!line.isEmpty()) {
                if (line.startsWith("Câu")) {
                    if (!isValidQuestionFormat(line)) {
                        isValid = false;
                        break;
                    } else {
                        qs.questionText = line;     //TODO
                    }
                } else if (line.startsWith("ANSWER:")) {
                    if (!isValidAnswerFormat(line)) {
                        isValid = false;
                        break;
                    } else {
                    	qs.questionAnswer = line;
                        question_List.add(qs);
                        qs = new Question();
                        
                    }
                } else {
                    if (!isValidChoiceFormat(line)) {
                        isValid = false;
                        break;
                    } else {
                        qs.choiceList.add(line);
                    }
                }
            }
        }

        reader.close();
        
        return isValid;
    }

    // Check answer format
    private static boolean isValidAnswerFormat(String line) {
        Pattern pattern = Pattern.compile("^ANSWER: [A-Z]$");
        Matcher matcher = pattern.matcher(line);
        return matcher.matches();
    }
    // --------------------//

    // Check questions format
    private static boolean isValidQuestionFormat(String line) {
        Pattern pattern = Pattern.compile("^Câu \\d+: .+");
        Matcher matcher = pattern.matcher(line);
        return matcher.matches();
    }
    // --------------------//
    // Check choice format
    private static boolean isValidChoiceFormat(String line) {
        Pattern pattern = Pattern.compile("^[A-Z]\\..*");
        Matcher matcher = pattern.matcher(line);
        Pattern pattern2 = Pattern.compile("^[A-Z]\\).*");
        Matcher matcher2 = pattern2.matcher(line);
        return  (matcher.matches() || matcher2.matches());
    }
}
