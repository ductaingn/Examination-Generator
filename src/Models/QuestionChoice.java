package Models;

import java.util.ArrayList;
import java.util.List;

public class QuestionChoice {
    private String choiceText;
    private Integer choiceId;
    private String choiceImageSource;
    public List<Double> choiceGrade;

    QuestionChoice(){
        double[] grade={100,90,500/6,80,75,70,400/6,60,50,40,200/6,30,25,20,100/6,100/7,100/8,100/9,10,5};
        for(int i=0;i<grade.length;i++){
            choiceGrade.add(grade[i]);
        }
        for(int i=grade.length-1;i>=0;i--){
            choiceGrade.add(-grade[i]);
        }
    }
    QuestionChoice(String choiceText){
        this.choiceText=choiceText;

        double[] grade={100,90,500/6,80,75,70,400/6,60,50,40,200/6,30,25,20,100/6,100/7,100/8,100/9,10,5};
        for(int i=0;i<grade.length;i++){
            choiceGrade.add(grade[i]);
        }
        for(int i=grade.length-1;i>=0;i--){
            choiceGrade.add(-grade[i]);
        }
    }
    public String getChoiceText() {
        return choiceText;
    }

    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }

    public Integer getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(Integer choiceId) {
        this.choiceId = choiceId;
    }

    public String getChoiceImageSource() {
        return choiceImageSource;
    }

    public void setChoiceImageSource(String choiceImageSource) {
        this.choiceImageSource = choiceImageSource;
    }
}
