package Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Question {
    private final StringProperty questionName = new SimpleStringProperty();
    public String getQuestionName() {
        return questionName.get();
    }
    public void setQuestionName(String questionName) {
        this.questionName.set(questionName);
    }
    public StringProperty questionNameProperty() {
        return questionName;
    }
}
