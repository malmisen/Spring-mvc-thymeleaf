/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import java.io.Serializable;

/**
 *
 * @author regularclip
 */
public class User implements Serializable {
    private String username;
    private String password;
    private int id;
    
    public User(){
        
    }
    
    public User(String username, String password, String email){
        this.username = username;
        this.password = password;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getPassword(){
        return password;
    }

    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    public boolean equals(User u){
        if(this.username.equals(u.getUsername()) && this.password.equals(u.getPassword())) return true;
        return false;
    }
    
}
