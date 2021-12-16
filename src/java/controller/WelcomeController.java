package controller;

import beans.Quizzes;
import beans.UserResults;
import db.QuizDAO;
import db.UserDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping; // for mapping request path to invoking method
import org.springframework.web.bind.annotation.RequestMethod; // for mapping request method (GET, POST) to controller method 
import org.springframework.web.bind.annotation.RequestParam; // for retrieving HTTP parameters sent (GET, POST)
import org.springframework.web.bind.annotation.SessionAttributes; // same as setattribute on a HttpSession-object

//@SessionAttributes("users")
@Controller
public class WelcomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String start(){ //method name is not mapped
        return "index"; // return filename without ".jsp" for jsp-pages in WEB-INF/jsp
    }
   
    @RequestMapping(value = "/quiz", method = RequestMethod.GET)
    public String submit(@RequestParam("username") String username, @RequestParam("password") String password, ModelMap model) {
         
        System.out.println("User: " + username);
        System.out.println("Pass." + password);
        
        beans.User user = new beans.User();
        user.setUsername(username);
        user.setPassword(password);
        /* Fetch User from DB*/
        UserDAO dao = new UserDAO();
        beans.User dbUser = dao.getUser(user);
        
      
        /* If User exists, fetch prior results, fetch quizzes*/
        if(dbUser.equals(user)){
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
            
        } else {
            return "index";
        } 
    }
    
    
    
    
    
    
    
}