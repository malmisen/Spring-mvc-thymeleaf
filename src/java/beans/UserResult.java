package beans;

import java.io.Serializable;

public class UserResult implements Serializable{
    private String subject;
    private int score;
    private int quizId;
    
    public UserResult(){
        
    }
    
    public String getQuiz(){
        return subject;
    }
    
    public int getScore(){
        return score;
    }
    
    public void setQuiz(String quiz){
        this.subject = quiz;
    }
    
    public void setScore(int score){
        this.score = score;
    }
    
    public void setQuizId(int id){
        quizId = id;
    }
    public int getQuizId(){
        return quizId;
    }
}
