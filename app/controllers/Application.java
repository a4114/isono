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
    public static Result connectComet(final String channelURI) {
    	return ok(new Comet("parent.getComment") { 
    		public void onConnected() {
    			CometManager.entrance(channelURI, this);
            }
    	});
    }
    
    //コメントを投稿
    public static Result postComment(final String channelURI) {
    	Map<String, String[]> requestBody = request().body().asFormUrlEncoded();
    	String context = requestBody.containsKey("text") ? requestBody.get("text")[0] : "";
    	Comment comment = new Comment("username", context, "tag", channelURI);
    	CometManager.sendComment(channelURI, comment);
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
    	//枠URI,Comet,ユーザを関連づける二次元ハッシュ
    	final static public HashMap<String, HashMap<Comet, User>> sockets = 
    			new HashMap<String, HashMap<Comet, User>>();
        
        //視聴ページアクセス時コレクションにCometとユーザを追加する
        public static void entrance(String channelURI, Comet comet) {
        	if(sockets.containsKey(channelURI)){
        		sockets.get(channelURI).put(comet, new User());
        	}else{
        		HashMap<Comet, User> map = new HashMap<Comet, User>();
        		map.put(comet, new User());
        		sockets.put(channelURI, map);
        	}
        }
        
        //コメントを受け取ってJson形式でクライアントに投げる
        public static void sendComment(String channelURI, Comment comment) {
        	JsonNode jcomment = Json.toJson(comment);
        	HashMap<Comet, User> map = sockets.get(channelURI);
        	for(Map.Entry<Comet,User> ck : map.entrySet()) {
        		ck.getKey().sendMessage(jcomment);
        	}
        }
    	
    }
}
