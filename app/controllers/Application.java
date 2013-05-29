package controllers;

import models.Comment;
import play.*;
import play.libs.Comet;
import play.libs.Akka;
import play.mvc.*;
import akka.actor.*;
import views.html.*;
import static java.util.concurrent.TimeUnit.*;
import scala.concurrent.duration.Duration;

public class Application extends Controller {
	final static ActorRef cometManager = CometManager.instance;
	
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
    			System.out.println("connect");
            }
    	});
    }
    
    //コメントを投稿
    public static Result updateComment(String channelURI) {
    	
    	
    	//視聴ページにリダイレクト
        return redirect("/watch/" + channelURI);
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
    public static class CometManager extends UntypedActor {
    	//
    	static ActorRef instance = Akka.system().actorOf(new Props(CometManager.class));
        // staticコンストラクタで実行間隔を指定
        static {
        	Akka.system().scheduler().schedule(
        			Duration.Zero(),
        			Duration.create(100, MILLISECONDS),
        			instance, "check",  Akka.system().dispatcher()
        			);
        }
        
        //コメントを受け取ったときの処理
        public void sentMessage() {
        	
        }
    	
    	public void onReceive(Object message) {
    		
    	}
    }
}
