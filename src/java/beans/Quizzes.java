/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author regularclip
 */
public class Quizzes implements Serializable {
    private ArrayList<Quiz> quizzes;
    private int size;
    public Quizzes(){
        size = 0;
        quizzes = new ArrayList<Quiz>();
    }
    
    public void addQuizzes(Quiz q){
        size++;
        quizzes.add(q);
    }
    
    public Quiz getQuiz(int i){
        return quizzes.get(i);
    }

    public int getSize() {
        return size;
    }
    
    public ArrayList<Quiz> getAllQuizzes(){
        return quizzes;
    }
    
}
