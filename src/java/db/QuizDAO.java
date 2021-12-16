/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import beans.Question;
import beans.Questions;
import beans.Quiz;
import beans.Quizzes;


/**
 *
 * @author regularclip
 */
public class QuizDAO {
    
    //QUIZZES TABLE
    private static final String QUIZZES_COLUMN_ID_NAME = "id";
    private static final String QUIZZES_COLUMN_SUBJECT_NAME = "subject";
    
    //QUESTION TABLE
    private static final String QUESTIONS_COLUMN_PRIMARY_KEY = "id";
    private static final String QUESTIONS_COLUMN_QUESTION_NAME = "text";
    private static final String QUESTIONS_COLUMN_OPTIONS_NAME = "options";
    private static final String QUESTIONS_COLUMN_ANSWER_NAME = "answer";
    
    
    private DBHandler db;
    private PreparedStatement getQuizzesStatement;
    private PreparedStatement getQuizQuestionsByQuizIdStatement;
   
    
    public QuizDAO(){
        try{
            db = DBHandler.getInstance();
            prepareStatements();
        }catch(SQLException e){
            System.out.println("Something went wrong when preparing statement!");
            e.printStackTrace();
        }
    }
    
    public Quizzes getQuizzes(){
        ResultSet set = null;
        Quizzes quizzes = new Quizzes();
        
        try {
            set = getQuizzesStatement.executeQuery();
            while(set.next()){
                Quiz q = new Quiz();
                q.setId(set.getInt(QUIZZES_COLUMN_ID_NAME));
                q.setSubject(set.getString(QUIZZES_COLUMN_SUBJECT_NAME));
                quizzes.addQuizzes(q);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return quizzes;
    }

    /* The param is quiz id!*/
    public Questions getQuestions(int id) {
        ResultSet set = null;
        Questions questions = new Questions();
        
        try {
            getQuizQuestionsByQuizIdStatement.setInt(1, id);
            set = getQuizQuestionsByQuizIdStatement.executeQuery();
            while(set.next()){
                Question q = new Question();
                q.setId(set.getInt(QUESTIONS_COLUMN_PRIMARY_KEY));
                q.setQuestion(set.getString(QUESTIONS_COLUMN_QUESTION_NAME));
                q.setOptions(set.getString(QUESTIONS_COLUMN_OPTIONS_NAME));
                q.setAnswer(set.getString(QUESTIONS_COLUMN_ANSWER_NAME));
                questions.addQuestion(q);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return questions;
    }
  
    
    private void prepareStatements() throws SQLException{
        getQuizzesStatement = db.getCon().prepareStatement("SELECT id, subject FROM quizzes");
        getQuizQuestionsByQuizIdStatement = db.getCon().prepareStatement("SELECT q.id, q.text,q.options,q.answer FROM questions AS q  INNER JOIN selector AS s WHERE q.id = s.question_id AND s.quiz_id = ?");
    }    

}
