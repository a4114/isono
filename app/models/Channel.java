package models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.JsonNode;

import play.libs.Comet;
import play.libs.Json;

public class Channel implements Comparable<Channel>{
    private final String channelName;
    private final String channelURI;
    private final User hostUser;
    private final HashMap<Comet,User> userMap;
    private final Timestamp startTime;
    private final List<Comment> commentList;
    
    public Channel(String channelName,String URI,User hostUser) {
        this.channelName = channelName;
        this.channelURI = URI;
        this.hostUser = hostUser;
        userMap = new HashMap<>();
        startTime = new Timestamp(System.currentTimeMillis());
        commentList = new ArrayList<>();
    }

    public String getChannelName() {
        return channelName;
    }

    public User getHostUser() {
        return hostUser;
    }

    public HashMap<Comet, User> getUserMap() {
        return userMap;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }
    
    //視聴者をリストに追加する
    public void AddWatchingUser(Comet comet,User user) {
        userMap.put(comet, user);
    }
    
    //最新コメントをリストに追加する
    private void AddCommentList(Comment comment){
        commentList.add(comment);
    }
   
    //視聴者にコメントを配信する（同時にリストに追加）
    public void BroadcastComment(Comment newComment){
        AddCommentList(newComment);
        for(Comet userComet : userMap.keySet()){
            JsonNode jcomment = Json.toJson(newComment);
            userComet.sendMessage(jcomment);
        }
    }

    public String getChannelURI() {
       
        return channelURI;
    }
    
    @Override
    public int hashCode(){
        return channelURI.hashCode();
    }

    @Override
    public int compareTo(Channel that) {
        // TODO 自動生成されたメソッド・スタブ
        return this.getChannelName().compareTo(that.getChannelName());
    }

    
}
