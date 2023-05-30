package Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Quiz {
    private final StringProperty quizName = new SimpleStringProperty();

    public String getQuizName() {
        return quizName.get();
    }

    public StringProperty quizNameProperty() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName.set(quizName);
    }
}
