package controllers;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonNode;

import models.Comment;
import models.User;

import play.*;
import play.libs.Comet;
import play.libs.Json;
import play.mvc.*;
import akka.actor.*;
import views.html.*;
import static java.util.concurrent.TimeUnit.*;
import scala.concurrent.duration.Duration;

public class Application extends Controller {

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
    public static Result updateComment(String channelURI) {
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
