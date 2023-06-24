package Controllers;

import javafx.scene.input.DragEvent;
import java.util.Vector;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.security.sasl.RealmChoiceCallback;
import javafx.scene.control.Button;
import Models.QQuestion;
import Models.Choice;
import Models.Model;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;


public class GUI21importTabController implements Initializable {
    @FXML
    private ImageView image;
    @FXML 
    private Button button;
    @FXML 
    private Button import_btn;
 // Liên kết với cơ sở dữ liệu :))
    QQuestion qs = new QQuestion();
    Choice choice = new Choice();
    Vector<QQuestion> questionList = new Vector<>();
    Vector<Choice> choiceList = new Vector<>();
    // --------------------//

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
            if (success == false) {
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
                    
                    for(int i = 0; i < questionList.size(); i++) {
                		System.out.println(questionList.elementAt(i).getText());
                	}
                    for(int i = 0 ; i < choiceList.size(); i++) {
                		System.out.println(choiceList.elementAt(i).getChoiceText());
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
                    
                    for(int i = 0; i < questionList.size(); i++) {
                		System.out.println(questionList.elementAt(i).getText());
                	}
                	for(int i = 0 ; i < choiceList.size(); i++) {
                		System.out.println(choiceList.elementAt(i).getChoiceText());
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

        while ((line = reader.readLine()) != null) {
            if (!line.isEmpty()) {
                if (line.startsWith("Câu")) {
                    if (!isValidQuestionFormat(line)) {
                        isValid = false;
                        break;
                    } else {
                        qs.setText(line); // câu hỏi được add
                        questionList.add(qs);
                        qs = new QQuestion();
                    }
                } else if (line.startsWith("ANSWER:")) {
                    if (!isValidAnswerFormat(line)) {
                        isValid = false;
                        break;
                    } else {
                        for (int i = 0; i < choiceList.size(); i++) {
                            if (line == choiceList.elementAt(i).getChoiceText()) {
                                choiceList.elementAt(i).setChoiceGrade((double) 100);
                            }
                        }
                    }
                } else {
                    if (!isValidChoiceFormat(line)) {
                        isValid = false;
                        break;
                    } else {
                        choice.setChoiceText(line); // Các lựa chọn được add
                        choiceList.add(choice);
                        choice = new Choice();
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
        return matcher.matches();
    }
    // --------------------//

}
