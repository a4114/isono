package controllers;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Channel;
import models.CometAddvanced;
import models.Comment;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.broadcast;
import views.html.index;
import views.html.newChannel;
import views.html.watch;

public class Application extends Controller {

    // <枠名><チャンネル>
    private static HashMap<String,Channel> channelList = new HashMap<>();
    private static int liveCount = 0;
    
    public static void AddUserToChannel(String channelURI,CometAddvanced cometAddvanced) throws Exception{
       
        if(channelList.containsKey(channelURI)){
            channelList.get(channelURI).AddWatchingUser(cometAddvanced);
        }else{
            throw new Exception("不正な枠URIです");
        }
    }
    
    
	//メインページにアクセス
    public static Result index() {
       Channel[] array= channelList.values().toArray(new Channel[0]);
       List<Channel> list = Arrays.asList(array);
       java.util.Collections.sort(list);
       
        return ok(index.render("メインページ",list));
    }
    
    //視聴ページにアクセス
    public static Result watch(String channelURI) {
        if(!channelList.containsKey(channelURI)){
            //枠が開いてない
            return notFound("枠が開いていません");
        }
        return ok(watch.render(channelURI));
        
    }
    
    //コメットソケットを作成
    public static Result connectComet(String channelURI) {
        
        CometAddvanced ca =new CometAddvanced("parent.getComment",channelURI,new User());

        //CometAddvancedを追加　初期化が違うくらいで後は一緒
    	return ok(ca);
    }
    
    //コメントを投稿
    public static Result postComment(String channelURI) {
        
    	Map<String, String[]> requestBody = request().body().asFormUrlEncoded();
    	String context = requestBody.containsKey("text") ? requestBody.get("text")[0] : "";
    	Comment comment = new Comment("username", context, "tag", "channel");
    	
    	//ここでComentCenterを使おう
    	if(channelList.containsKey(channelURI)){
    	    channelList.get(channelURI).BroadcastComment(comment);
    	}else{
    	    return internalServerError("投稿先の枠がありません");
    	}
    	return ok("");
    }
    
    //枠作成ページにアクセス
    public static Result newChannel() {
    	return ok(newChannel.render());
    }

    //枠を作成
    public static Result updateChannel() {
    	String channelURI = "lv"+liveCount++;
    	
    	//チャンネルリストに新しいチャンネルを追加
    	channelList.put(channelURI, (new Channel("光り手チャンネル"+liveCount,channelURI, new User())));
    	
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
    
/*    //Comet管理クラス
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
    	
    }*/
}
