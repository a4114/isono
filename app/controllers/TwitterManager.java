package controllers;

import org.apache.commons.exec.ExecuteException;

import play.cache.Cache;
import scala.util.control.Exception;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;
import views.html.newChannel;

public class TwitterManager {
    static public final String CONSUMER_KEY = "tIRpAmBs6UtryMFGPL4wg";
    static public final String CONSUMER_SECRET = "3Uak8Nk6euyBzxRsitDZTwrnSTZcZKTZlHe3WGwXmk";

    
    public static String getLoginUrl() throws TwitterException {
        TwitterFactory twitterFactory = new TwitterFactory();
        Twitter twitter = twitterFactory.getInstance();
        twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);

        try {
            RequestToken reqToken = twitter.getOAuthRequestToken();
            Cache.set("RequestToken", reqToken);
            Cache.set("TwitterForOAuth", twitter);
            return reqToken.getAuthorizationURL();
        } catch (TwitterException e) {
            throw (e);
        }

    }

/*    public static Twitter getTwitter() {
        Twitter twitter = (Twitter) Cache.get("isonoTwitter");

        try {
            twitter.getScreenName();
            return twitter;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return null;
    }*/

    public static Twitter makeTwitter(String OauthVerifier)
            throws TwitterException {
        
        Twitter twitter = (Twitter) Cache.get("TwitterForOAuth");

        
        if (twitter == null) {
            throw new TwitterException("TwitterCache is null.");
        }

        try {
            AccessToken accessToken = twitter
                    .getOAuthAccessToken(OauthVerifier);
            twitter.setOAuthAccessToken(accessToken);
        } catch (TwitterException e) {
            throw (e);
        }

        Cache.remove("RequestToken");
        Cache.remove("TwitterForOAuth");
        return twitter;
    }

}