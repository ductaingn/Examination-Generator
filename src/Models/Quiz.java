package Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Quiz {
    private final StringProperty quizTime = new SimpleStringProperty();
    private final StringProperty quizName = new SimpleStringProperty();
    private final StringProperty quizId = new SimpleStringProperty();

    public String getQuizTime() {
        return quizTime.get();
    }

    public StringProperty quizTimeProperty() {
        return quizTime;
    }

    public String getQuizName() {
        return quizName.get();
    }

    public StringProperty quizNameProperty() {
        return quizName;
    }

    public void setQuizTime(String quizTime) {
        this.quizTime.set(quizTime);
    }

    public void setQuizName(String quizName) {
        this.quizName.set(quizName);
    }

    public String getQuizId() {
        return quizId.get();
    }

    public StringProperty quizIdProperty() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId.set(quizId);
    }
}
