package Controllers;

import Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ButtonType;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.sql.SQLException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;

public class GUI72Controller implements Initializable {
    @FXML
    private Button cancel_btn;
    @FXML
    private Button close_btn;
    @FXML
    private Button start_btn;
    @FXML
    private Button export_btn;
    @FXML
    private Label switch_lbl;
    @FXML
    private Label content_lbl;
    public static String idData, nameData, timeData;
    public void showGUI61() {
        Stage stage = (Stage)switch_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showGUI61();
    }
    public void showGUI73() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/GUI73.fxml"));
            Parent root = loader.load();
            GUI73Controller gui73Controller = loader.getController();
            gui73Controller.getInfo(idData, nameData, timeData);

            Stage stage = (Stage)switch_lbl.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
            Model.getInstance().getViewFactory().showGUI73();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
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
    public void getInfo(String quiz_id, String quiz_name, String quiz_time) {
        idData = quiz_id;
        nameData = quiz_name;
        timeData = quiz_time;
    }
    
    public void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public void getPdf(){
        // Hiển thị hộp thoại lưu file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(null);



        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "select qs.question_id, qs.text, qs.image, c.content, c.image " +
                    "from question qs join choice c on qs.question_id = c.question_id " +
                    "join ques_quiz qq on qq.question_id = qs.question_id where qq.quiz_id = " + idData);
            ResultSet resultSet = preparedStatement.executeQuery();
            int currentId = 0; int count = 0;
            
            if(file != null) {
            	Document document = new Document();
            	PdfWriter.getInstance(document, new FileOutputStream(file));
            	document.open();            	
            	while (resultSet.next()) {
            		if (resultSet.getInt("question_id") == currentId) {
            			String s = (char)(98+count) + ".  " + resultSet.getString("content");
            			System.out.println(s);
            			document.add(new Paragraph(s));
                        if(resultSet.getBinaryStream("c.image")!=null){
                            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(resultSet.getBytes("c.image"));
                            image.scaleToFit(400,200);
                            document.add(image);
                        }
            			count++;
            		} else {
            			
            			String s1 = resultSet.getString("text");
            			
            			String s2 = "a.  " + resultSet.getString("content");
            			System.out.println("---");
            			document.add(new Paragraph("\n"));
            			System.out.println(s1);
            			System.out.println(s2);
            			
            			document.add(new Paragraph(s1));
                        if(resultSet.getBinaryStream("qs.image")!=null){
                            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(resultSet.getBytes("qs.image"));
                            image.scaleToFit(400,200);
                            document.add(image);
                        }

            			document.add(new Paragraph(s2));
                        if(resultSet.getBinaryStream("c.image")!=null){
                            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(resultSet.getBytes("c.image"));
                            image.scaleToFit(400,200);
                            document.add(image);
                        }
                        
            			currentId = resultSet.getInt("question_id");
            			count = 0;                  
            		}
            	}
            	
            	document.close();
            	System.out.println(file.getAbsolutePath());
            	System.out.println("PDF exported successfully.");
            	
            	Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Bạn có muốn thiết lập mật khẩu cho File không!");

                // Tạo các nút Yes và No
                ButtonType yesButton = new ButtonType("Yes");
                ButtonType noButton = new ButtonType("No");

                // Đặt các nút vào hộp thoại thông báo
                alert.getButtonTypes().setAll(yesButton, noButton);

                // Hiển thị hộp thoại và chờ người dùng chọn
                alert.showAndWait().ifPresent(response -> {
                    if (response == yesButton) {
                    	Stage primaryStage = new Stage();
                    	primaryStage.setTitle("PDF Encryption");

                        // Tạo giao diện người dùng
                        Label passwordLabel = new Label("Mật khẩu:");
                        PasswordField passwordField = new PasswordField();
                        Button setButton = new Button("Thiết lập mật khẩu");

                        setButton.setOnAction(event -> {
                            String password = passwordField.getText();

                            // Lấy đường dẫn tới tệp PDF
                            String filePath = file.getAbsolutePath();

                            try {
                                // Mở tệp PDF
                                File file1 = new File(filePath);
                                PDDocument document1 = PDDocument.load(file1);

                                // Thiết lập mật khẩu và mã hóa tài liệu PDF
                                StandardProtectionPolicy protectionPolicy = new StandardProtectionPolicy(filePath, filePath, null);
                                protectionPolicy.setUserPassword(password);

                                AccessPermission accessPermission = new AccessPermission();
                                accessPermission.setCanPrint(true);

                                protectionPolicy.setPermissions(accessPermission);

                                document1.protect(protectionPolicy);

                                // Lưu tệp PDF đã được thiết lập mật khẩu
                                document1.save(file1);
                                document1.close();

                                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Mật khẩu đã được thiết lập thành công cho tệp PDF.");
                                primaryStage.close();
                                showGUI61();
                            } catch (IOException e) {
                                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể thiết lập mật khẩu cho tệp PDF.");
                                primaryStage.close();
                                showGUI61();
                            }
                        });

                        VBox vbox = new VBox(passwordLabel, passwordField, setButton);
                        vbox.setSpacing(10);
                        vbox.setPadding(new Insets(10));

                        primaryStage.setScene(new Scene(vbox, 300, 150));
                        primaryStage.show();
                    	
                        System.out.println("Người dùng đã chọn Yes.");
                    } else if (response == noButton) {
                        System.out.println("Người dùng đã chọn No.");
                    }
                });
            	
            	
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        content_lbl.setText("Your attempt will have a time of limit of " + timeData + " minutes. " +
                "When you start, the timer will begin to count down and cannot be paused. " +
                "You must finish your attempt before it expires. Are you sure you wish to start now?");
        cancel_btn.setOnAction(event -> showGUI61());
        close_btn.setOnAction(event -> showGUI61());
        start_btn.setOnAction(event -> showGUI73());
        export_btn.setOnAction(event -> {
            getPdf();
        });
    }
}