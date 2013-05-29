package controllers;

import models.Comment;
import play.*;
import play.libs.Comet;
import play.mvc.*;

import views.html.*;

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
    public static class CometManager {
    	
    }
}
