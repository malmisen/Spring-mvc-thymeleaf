/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author regularclip
 */
public class DBHandler {
    
    private static Connection con = null;
    private static DBHandler instance;
    private static Lock lock = new ReentrantLock();
    private static final String dbUsername = "root";
    private static final String dbPassword = "regular";
 
    public DBHandler(){
        if(con == null){
            //Start database connection
            connect();
        }
    }
    
    private void connect(){
        System.out.println("Connecting to database...");
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", dbUsername, dbPassword);
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        
    }
    
    /*Get instance of singleton*/
    public static DBHandler getInstance(){
        if(instance == null){
            synchronized(lock){
                if(instance == null){
                    instance = new DBHandler();
                }
            }
        }
        return instance;
    }
    
    public Connection getCon(){
        return con;
    }
    
    public void commitWork(){
        try{
            con.commit();
        }catch (SQLException e){
            System.out.println("COULD NOT COMMIT WORK");
            e.printStackTrace();
        }
    }
    
}
