package models;

import play.cache.Cache;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;



public class TwitterManager{
	static public final String CONSUMER_KEY = "tIRpAmBs6UtryMFGPL4wg";
    static public final String CONSUMER_SECRET = "3Uak8Nk6euyBzxRsitDZTwrnSTZcZKTZlHe3WGwXmk";	
	
	public static String getLoginUrl() throws Exception{
		TwitterFactory twitterFactory = new TwitterFactory();
        Twitter twitter = twitterFactory.getInstance();
        twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);

        try {
            RequestToken reqToken = twitter.getOAuthRequestToken();
            Cache.set("RequestToken", reqToken);
            Cache.set("Twitter", twitter);
            return reqToken.getAuthorizationURL();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
	
        throw new Exception("Twitterが認証URLくれない");
//        return ""; // errorなら""かえす
	}

	
	public static Twitter getTwitter(){
        Twitter twitter = (Twitter)Cache.get("Twitter");
          	
    	try {
    		twitter.getScreenName();
    		return twitter;
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
    	return null;		
	}

	
	
	
	
	
}