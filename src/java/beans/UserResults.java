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
public class UserResults implements Serializable{
    ArrayList<UserResult> results;
    int size;
    
    public UserResults(){
        size = 0;
        results = new ArrayList<UserResult>();
    }
    
    public void addResults(UserResult result){
        size++;
        results.add(result);
    }
    public int getSize(){
        return size;
    }
    public UserResult getResult(int i){
        return results.get(i);
    }
    
    public ArrayList<UserResult> getResults(){
        return results;
    }
}
