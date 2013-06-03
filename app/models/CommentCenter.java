package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import controllers.Application;


public final class CommentCenter {
  
    // key : ChannelName
    // Value : コメントリスト
    public final static HashMap<String,List<Comment>> commentListMap = new HashMap<>();

    public static void AddComment(Comment comment){
        String channelURI = comment.getChannelURI();
        
        //既にコレクションが存在するか？
        if( commentListMap.containsKey(channelURI) ){
            //存在するならコレクションの最後に追加
            commentListMap.get(channelURI).add(comment);
        }else{
            
            //存在しないならArrayListを作ってマップに追加
            ArrayList<Comment> list = new ArrayList<Comment>();
            list.add(comment);
            commentListMap.put(channelURI, list);         
        }
        
        
        //コメントを投げる
        Application.updateComment(comment);
        
    }
    
    
    
}
