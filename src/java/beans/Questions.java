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
public class Questions implements Serializable{
   private ArrayList<Question> questions;
   private int size;
   
   public Questions(){
       questions = new ArrayList<Question>();
       size = 0;
   }
   
   public int getSize(){
       return size;
   }
   
   public void addQuestion(Question q){
       questions.add(q);
       size++;
   }
   
   public Question getQuestion(int i){
       return questions.get(i);
   }
   
   public ArrayList<Question> getListOfQuestions(){
       return questions;
   }
    
}
