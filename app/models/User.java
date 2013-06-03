package models;

import java.util.HashMap;

import twitter4j.Twitter;
import twitter4j.TwitterException;

public final class User {
    public final String IPAddress;
    public final Twitter twitter;
    
    public static HashMap<Long,User> userList = new HashMap<>();
    
    
    public User( Twitter twitter ,String IPAddress) {
        this.twitter = twitter;
        this.IPAddress = IPAddress;
        
    }
    
    public User() {
        System.out.println("[User]-[DebugConstractor]");
        this.twitter = null;
        this.IPAddress = "test";
    }
    
    public static void AddUser(User user) throws IllegalStateException,TwitterException{
        try {
            userList.put( user.twitter.getId() , user);
        } catch (IllegalStateException e) {
            
            throw(e);
        } catch (TwitterException e) {
            
            throw(e);
        }
    }
 
} 