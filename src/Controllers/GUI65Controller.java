package Controllers;

import Models.Model;
import Models.QQuestion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import static Controllers.GUI63aItemController.prepareToAdd;

public class GUI65Controller implements Initializable {
    private final int itemPerPage = 10;
    @FXML
    private CheckBox Include_ckb;
    @FXML
    private Pagination pagination;
    @FXML
    private Label switch_lbl;
    @FXML
    private Button close_btn;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private ComboBox<Integer> comboBox1;
    @FXML
    private TableColumn<QQuestion, String> questionColumn;
    @FXML
    private TableView<QQuestion> table;
    @FXML
    private CheckBox include_ckb;
    @FXML
    private Button add_btn;
    private static String selectedCategory;
    private static ObservableList<QQuestion> questionList = FXCollections.observableArrayList();

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

    public void getComboBox() {
        String queryCategoryName = "" +
                "SELECT CONCAT( REPEAT(' ', COUNT(parent.name) - 1),' ' ,node.name,' (', " +
                "(SELECT COUNT(question_id) FROM question " +
                "WHERE question.category_id=node.category_id),') '  ) AS name " +
                "FROM category AS node,category AS parent " +
                "WHERE node.lft BETWEEN parent.lft AND parent.rgt " +
                "GROUP BY node.category_id ORDER BY node.lft;";
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryCategoryName);
            ResultSet resultSet = preparedStatement.executeQuery();
            ObservableList<String> categoryName = FXCollections.observableArrayList();
            while (resultSet.next()) {
                String item = resultSet.getString("name");
                categoryName.add(item);
            }
            comboBox.setItems(categoryName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getComboBox1() {
        comboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            String selectedItem = newValue.toString();
            int max = Integer.parseInt(selectedItem.substring(selectedItem.lastIndexOf("(") + 1, selectedItem.lastIndexOf(")")));
            comboBox1.getItems().clear();
            if (max == 0) {
                comboBox1.setDisable(true);
            } else {
                comboBox1.setDisable(false);
                for (int i = 1; i <= max; i++) {
                    comboBox1.getItems().add(i);
                }
                comboBox1.setValue(1);
            }
        });
    }

    public void showGUI62a(){
        Stage stage = (Stage) switch_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showGUI62a();
    }
    public void showGUI62a_add() {
        for (int i = 0; i < questionList.size(); i++){
            prepareToAdd.add(String.valueOf(questionList.get(i).getQuestion_id()));
        }
        Collections.shuffle(prepareToAdd);
        for (int i = 0; i < comboBox1.getValue(); i++) {
            System.out.println(prepareToAdd.get(i) + " fas");
        }
        List<String> sub  = prepareToAdd.subList(0, comboBox1.getValue());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/GUI62a.fxml"));
            Parent root = loader.load();
            GUI62aController gui62aController = loader.getController();
            gui62aController.insertQuesToQuizI(sub);

            Stage stage = (Stage) switch_lbl.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
            Model.getInstance().getViewFactory().showGUI62a();
        } catch (Exception e) {e.printStackTrace();}
    }
    private void loadQuestion() {
        try {
            questionList.clear();
            String query = "SELECT q.name, q.question_id FROM question q, category c WHERE q.category_id = c.category_id AND c.name = ?";
            String selectedCategory2 = selectedCategory.substring(0, selectedCategory.indexOf('(')-1).trim();
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, selectedCategory2);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                QQuestion question = new QQuestion();
                question.setName(resultSet.getString("name"));
                question.setQuestion_id(resultSet.getInt("question_id"));
                questionList.add(question);
            }
            questionColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        table.setItems(questionList);
        table.refresh();
        pagination.setPageCount(questionList.size() / itemPerPage + 1);
        pagination.setPageFactory(this::createPage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getComboBox();
        getComboBox1();
        comboBox.setOnAction(event -> {
            selectedCategory = comboBox.getValue();
            loadQuestion();
        });
        add_btn.setOnAction(event -> {
            showGUI62a_add();
            prepareToAdd.clear();
        });
        close_btn.setOnAction(event -> showGUI62a());
    }

    private Node createPage(int pageIndex) {
        int from = pageIndex * itemPerPage;
        int to = Math.min(from + itemPerPage, questionList.size());
        ObservableList<QQuestion> sublist = FXCollections.observableArrayList(questionList.subList(from, to));
        table.setItems(sublist);
        if (pageIndex >= pagination.getPageCount()) {
            return null;
        }
        return table;
    }
}