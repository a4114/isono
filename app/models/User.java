package models;

import twitter4j.Twitter;

public final class User {
    public final String IPAddress;
    public final Twitter twitter;
    
    public User(Twitter twitter,String IPAddress) {
        this.twitter = twitter;
        this.IPAddress = IPAddress;
    }
    
    public User() {
        System.out.println("[User]-[DebugConstractor]");
        this.twitter = null;
        this.IPAddress = "test";
    }
} 