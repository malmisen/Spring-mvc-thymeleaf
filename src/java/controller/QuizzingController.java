/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import beans.Question;
import beans.Questions;
import beans.Quizzes;
import beans.User;
import beans.UserResult;
import db.QuizDAO;
import db.UserDAO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author regularclip
 */
@Controller
public class QuizzingController {
    @RequestMapping(value = "/quizzing", method = RequestMethod.GET)
    public String submit(@RequestParam("quizId") int id, @RequestParam("userId") int userId, ModelMap model) {
         
        QuizDAO dao = new QuizDAO();
        Questions questions = dao.getQuestions(id);
       
        model.addAttribute("quizId", id);
        model.addAttribute("userId", userId);
        model.addAttribute("question1", questions.getQuestion(0));
        model.addAttribute("question2", questions.getQuestion(1));
        model.addAttribute("question3", questions.getQuestion(2));
     
        return "quizzing.html";
    }
    
    @RequestMapping(value = "/result", method = RequestMethod.POST)
    public String result(@RequestParam("quizId") int id, @RequestParam("userId") int userId, HttpServletRequest req, ModelMap model) {
        Enumeration<String> parameters = req.getParameterNames();
        ArrayList<String> params = Collections.list(parameters);
        params.remove(0); //remove userId
        params.remove(0); //remove quizId
        
        
        QuizDAO dao = new QuizDAO();
        Questions questions = dao.getQuestions(id);
        
        int[] answers = {0,0,0,0,0,0,0,0,0};
        
        for(int i = 0; i < params.size(); i++){
            answers[Integer.parseInt(params.get(i))] = 1; 
        }
        
      
        int totalPoints = 0;
        int i = 0;
        for(Question q: questions.getListOfQuestions()){
            StringBuilder str = new StringBuilder();
                for(int j = 0; j < 3; j++){
                    str.append(answers[j+i] + "/");
                }
                str.setLength(5);
                System.out.println("Answers: " + str.toString());
                if(q.deservesPoint(str.toString())) totalPoints++;
                i += 3;
        }
        
        System.out.println("Total points: " + totalPoints);
        
        UserResult result = new UserResult();
        result.setQuizId(id);
        result.setScore(totalPoints);
        
        
        UserDAO userDAO = new UserDAO();
        User userDB = userDAO.getUserById(userId);
        
        userDAO.updateUserResults(userDB, result);
        
        Quizzes quizzes = dao.getQuizzes();
        
        model.addAttribute("user", userDB);
        model.addAttribute("results", userDAO.getUserResults(userDB).getResults());
        model.addAttribute("quizzes", quizzes.getAllQuizzes());

        return "quiz.html";
    }
}
