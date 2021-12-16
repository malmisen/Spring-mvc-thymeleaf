/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import beans.Quizzes;
import beans.User;
import beans.UserResults;
import db.QuizDAO;
import db.UserDAO;
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
public class RegisterController {
    @RequestMapping(value = "/reg", method = RequestMethod.GET)
    public String getRegister( ModelMap model) {
         
        return "register.html";
    }
    
     @RequestMapping(value = "/registerMe", method = RequestMethod.POST)
    public String submit(@RequestParam("username") String username, @RequestParam("password") String password, ModelMap model) {
         
        System.out.println("User: " + username);
        System.out.println("Pass." + password);
        
        User user = new beans.User();
        user.setUsername(username);
        user.setPassword(password);
        /* Fetch User from DB*/
        UserDAO dao = new UserDAO();
        User dbUser = dao.getUser(user);
        
        if(dbUser.getUsername() != null){
            return "register.html";
        } else {
            dao.createNewUser(user);
            dbUser = dao.getUser(user);
            /* Fetch prior results */
            UserResults results = dao.getUserResults(dbUser);
            
            /* Fetch Available quizzes */
            QuizDAO quizDAO = new QuizDAO();
            Quizzes quizzes = quizDAO.getQuizzes();
            
            /* Provide data*/
            model.addAttribute("user", dbUser);
            model.addAttribute("results", results.getResults());
            model.addAttribute("quizzes", quizzes.getAllQuizzes());
            
            return "quiz.html";
        }
      
        
    }
}
