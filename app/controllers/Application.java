package controllers;

import java.util.HashMap;
import java.util.Map;

import models.Comment;
import models.User;

import org.codehaus.jackson.JsonNode;

import play.cache.Cache;
import play.libs.Comet;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;
import views.html.broadcast;
import views.html.index;
import views.html.newChannel;
import views.html.watch;

public class Application extends Controller {
    static private final String CONSUMER_KEY = "tIRpAmBs6UtryMFGPL4wg";
    static private final String CONSUMER_SECRET = "3Uak8Nk6euyBzxRsitDZTwrnSTZcZKTZlHe3WGwXmk";
    
	//メインページにアクセス
    public static Result index() {
        return ok(index.render("メインページ"));
    }
    
    //視聴ページにアクセス
    public static Result watch(String channelURI) {
        return ok(watch.render(channelURI));
    }
    
    //コメットソケットを作成
    public static Result connectComet(String channelURI) {
    	return ok(new Comet("parent.getComment") { 
    		public void onConnected() {
    			CometManager.entrance(this);
            }
    	});
    }
    
    //コメントを投稿
    public static Result postComment(String channelURI) {
    	Map<String, String[]> requestBody = request().body().asFormUrlEncoded();
    	String context = requestBody.containsKey("text") ? requestBody.get("text")[0] : "";
    	Comment comment = new Comment("username", context, "tag", "channel");
    	CometManager.sendComment(comment);
    	return ok("");
    }
    
    //枠作成ページにアクセス
    public static Result newChannel() {
    	return ok(newChannel.render());
    }

    //枠を作成
    public static Result updateChannel() {
    	String channelURI = "lv999999";
    	//配信ページにリダイレクト
    	return redirect("/broadcast/" + channelURI);
    }

    //配信ページにアクセス
    public static Result broadcast(String channelURI) {
        return ok(broadcast.render(channelURI));
    }
    
    //投稿者コメント
    public static Result updateOwnerComment(String channelURI) {
        return redirect("/broadcast/" + channelURI);
    }
   
   

    public static Result login(){
        TwitterFactory twitterFactory = new TwitterFactory();
        Twitter twitter = twitterFactory.getInstance();
        twitter.setOAuthConsumer(CONSUMER_KEY,CONSUMER_SECRET);

        try {
            RequestToken reqToken = twitter.getOAuthRequestToken();

            Cache.set("RequestToken", reqToken);
            Cache.set("Twitter", twitter);
            return redirect(reqToken.getAuthorizationURL());
        } catch (TwitterException e) {
            e.printStackTrace();
            return internalServerError("Twitter4jの例外");
        }
    }
    
//    public static Result checkLoginState


    public static Result twitterCallback(){
        String oauth_token = request().queryString().get("oauth_token")[0];
        session("oauth_token", oauth_token);
        String oauth_verifier = request().queryString().get("oauth_verifier")[0];
        session("oauth_verifier", oauth_verifier);
      
        return internalServerError("oauth_token = " + oauth_token + "\n" + "oauth_verifier = " + oauth_verifier);
    }


    public static Result checkLoginState(){
    	String value="";
    	String oauth_token = session("oauth_token");
    	String oauth_verifier = session("oauth_verifier");

    	ConfigurationBuilder cb = new ConfigurationBuilder();
    	cb.setDebugEnabled(true)
    	  .setOAuthConsumerKey(CONSUMER_KEY)
    	  .setOAuthConsumerSecret(CONSUMER_SECRET)
    	  .setOAuthAccessToken(oauth_token)
    	  .setOAuthAccessTokenSecret(oauth_verifier);
    	
    	TwitterFactory tf = new TwitterFactory(cb.build());
    	Twitter twitter = tf.getInstance();
    	
    	
    	String name="dummy";
    	try {
    		name = twitter.getScreenName();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	value += "oauth_token = " + oauth_token + "\noauth_secret = " + oauth_verifier + "\nようこそ、 " + name + "さん☝( ◠‿◠ )☝";
		return internalServerError(value);
//		return(internalServerError("☝( ◠‿◠ )☝ログインできなかったよ"));    		    	
//    	return internalServerError("☝( ◠‿◠ )☝oauth_token = " + token + "\n" + "oauth_verifier = " + token_secret);    	
    }
    
    
    public static void UpdateComment(Comment comment){
        
    }  
    
    //Comet管理クラス
    public static class CometManager{
    	//キー：Comet　値：ユーザ
    	final static public HashMap<Comet,User> sockets = new HashMap<Comet,User>();
        
        //視聴ページにアクセス時にコレクションにCometとユーザを追加する
        public static void entrance(Comet comet) {
        	sockets.put(comet, new User());
        }
        
        //コメントを受け取ってJson形式でクライアントに投げる
        public static void sendComment(Comment comment) {
        	JsonNode jcomment = Json.toJson(comment);
        	for(Map.Entry<Comet,User> ck : sockets.entrySet()) {
        		ck.getKey().sendMessage(jcomment);
        	}
        }
    	
    }
}
