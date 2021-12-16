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
import beans.User;
import beans.UserResult;
import beans.UserResults;

/**
 *
 * @author regularclip
 */
public class UserDAO {
    
    private static final String USER_TABLE_NAME = "users";
    
    //USER TABLE
    private static final String USER_COLUMN_USERNAME_NAME = "username";
    private static final String USER_COLUMN_PASSWORD_NAME = "password";
    private static final String USER_COLUMN_ID_NAME = "id";
    
    //QUIZZES TABLE
    private static final String QUIZZES_COLUMN_SUBJECT_NAME = "subject";
    private static final String QUIZZES_COLUMN_ID_NAME = "id";
    
    //RESULTS TABLE
    private static final String RESULTS_COLUMN_SCORE_NAME = "score";
    
    private DBHandler db;
    private PreparedStatement createNewUserStatement;
    private PreparedStatement searchExistingUserStatement;
    private PreparedStatement getUserQuizResults;
    private PreparedStatement updateUserQuizResultsStatement;
    private PreparedStatement getUserByUserIdStatement;
    
    public UserDAO(){
        try{
            db = DBHandler.getInstance();
            prepareStatements();
        }catch(SQLException e){
            System.out.println("Something went wrong when preparing statement!");
            e.printStackTrace();
        }
    }
    
    
    public User getUser(User user) {
        ResultSet result = null;
        User dbUser = new User();
        try{
            searchExistingUserStatement.setString(1, user.getUsername());
            result = searchExistingUserStatement.executeQuery();
            while(result.next()){
                dbUser.setUsername(result.getString(USER_COLUMN_USERNAME_NAME));
                dbUser.setPassword(result.getString(USER_COLUMN_PASSWORD_NAME));
                dbUser.setId(result.getInt(USER_COLUMN_ID_NAME));
            }
        }catch(SQLException e){
            System.out.println("Something went wrong when executing query");
            e.getStackTrace();
        }
        return dbUser;
    }

   
   
    public boolean createNewUser(User user) {
       
        int updatedRows = 0;//keep count of the number of updated rows
        try{
            createNewUserStatement.setString(1, user.getUsername());
            createNewUserStatement.setString(2, user.getPassword());
            updatedRows = createNewUserStatement.executeUpdate();
            if(updatedRows != 1){
                System.out.println("COULD NOT UPDATE ROW");
            }
        }catch(SQLException e){
            System.out.println("Woops, something went wrong when creating new user in DB");
            e.getStackTrace();
        }
        return updatedRows == 1;
        
    }
    
    public UserResults getUserResults(User user){
        ResultSet resultSet = null;
        UserResults results = new UserResults();
        UserResult result = null;
        try{
            getUserQuizResults.setInt(1, user.getId());
            resultSet = getUserQuizResults.executeQuery();
            int count = 0;
            while(resultSet.next()){
                result = new UserResult();
                result.setQuiz(resultSet.getString(QUIZZES_COLUMN_SUBJECT_NAME));
                result.setScore(resultSet.getInt(RESULTS_COLUMN_SCORE_NAME));
                result.setQuizId(resultSet.getInt(QUIZZES_COLUMN_ID_NAME));
    
                results.addResults(result);
                count++;
            }
            
        }catch(SQLException e){
            System.out.println("Something went wrong when fetching quiz results");
        }
        
        return results;
    }
    
    public boolean updateUserResults(User user, UserResult result){
        
        System.out.println("INSIDE UPDATE FUNCTION: ");
        System.out.println("quizId: " + result.getQuizId());
        int updatedRows = 0;
        try {
            updateUserQuizResultsStatement.setInt(1, user.getId());
            updateUserQuizResultsStatement.setInt(2, result.getQuizId());
            updateUserQuizResultsStatement.setInt(3, result.getScore());
            updatedRows = updateUserQuizResultsStatement.executeUpdate();
            if(updatedRows == 1) return true;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public User getUserById(int id) {
        System.out.println("I GOT ID " + id);
        ResultSet result = null;
        User dbUser = new User();
        try{
             getUserByUserIdStatement.setInt(1, id);
            result =  getUserByUserIdStatement.executeQuery();
            while(result.next()){
                dbUser.setUsername(result.getString(USER_COLUMN_USERNAME_NAME));
                dbUser.setPassword(result.getString(USER_COLUMN_PASSWORD_NAME));
                dbUser.setId(result.getInt(USER_COLUMN_ID_NAME));
            }
        }catch(SQLException e){
            System.out.println("Something went wrong when executing query");
            e.getStackTrace();
        }
        System.out.println("Found user info: ");
        System.out.println(dbUser.getUsername());
        return dbUser;
    }
    
    private void prepareStatements() throws SQLException{
        searchExistingUserStatement = db.getCon().prepareStatement("SELECT username, password, id FROM users WHERE username = ?");
        createNewUserStatement = db.getCon().prepareStatement("INSERT INTO users (username,password) VALUES (?,?)");
        getUserQuizResults = db.getCon().prepareStatement("SELECT q.subject, q.id,r.score FROM quizzes AS q INNER JOIN results AS r WHERE q.id = r.quiz_id AND r.user_id = ?");
        updateUserQuizResultsStatement = db.getCon().prepareStatement("INSERT INTO results (user_id, quiz_id, score) VALUES (?,?,?)");
        getUserByUserIdStatement = db.getCon().prepareStatement("SELECT username, password, id FROM users WHERE id = ?" );
    }    

   
    
}
