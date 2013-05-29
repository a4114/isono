package controllers;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonNode;

import controllers.Application.User;

import models.Comment;
import play.*;
import play.libs.Comet;
import play.libs.Akka;
import play.libs.Json;
import play.mvc.*;
import akka.actor.*;
import views.html.*;
import static java.util.concurrent.TimeUnit.*;
import scala.concurrent.duration.Duration;

public class Application extends Controller {
	final static CometManager cometManager = new CometManager;
	
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
    			cometManager.entrance(this);
            }
    	});
    }
    
    //コメントを投稿
    public static Result updateComment(String channelURI) {
    	Map<String, String[]> requestBody = request().body().asFormUrlEncoded();
    	String context = requestBody.containsKey("text") ? requestBody.get("text")[0] : "";
    	Comment comment = new Comment("username", context, "tag", "channel");
    	cometManager.tell(comment, null);
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
    
    public static class User {
    }   
    
    //Comet管理クラス
    public static class CometManager extends UntypedActor {
    	//Actor
    	final static ActorRef instance = Akka.system().actorOf(new Props(CometManager.class));
    	final static public HashMap<Comet,User> sockets = new HashMap<Comet,User>();
        
        //
        public void entrance(Comet comet) {
        	sockets.put(comet, new User());
        }
        
        //コメントを受け取ったときの処理
        public void sendComment(Comment cmtObj) {
        	JsonNode comment = Json.toJson(cmtObj);
        	for(Map.Entry<Comet,User> ck : sockets.entrySet()) {
        		ck.getKey().sendMessage(comment);
        	}
        }
    	
    	public void onReceive(Object message) {
    		if(message instanceof Comment) {
    			this.sendComment((Comment)message);
    		}
    	}
    }
}
