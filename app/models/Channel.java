package models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.Props;

public class Channel implements Comparable<Channel>{
    private final String channelName;
    private final String channelURI;
    private final User hostUser;
    private final ActorRef cometUserManager;
    private final Timestamp startTime;
    private final List<Comment> commentList;
    
    public Channel(String channelName,String URI,User hostUser) {
        this.channelName = channelName;
        this.channelURI = URI;
        this.hostUser = hostUser;
        
        //ここ
        cometUserManager = play.libs.Akka.system().actorOf(new Props(CometUserManager.class));
        
        startTime = new Timestamp(System.currentTimeMillis());
        commentList = new ArrayList<>();
    }

    public String getChannelName() {
        return channelName;
    }

    public User getHostUser() {
        return hostUser;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }
    
    //視聴者をリストに追加する
    public void AddWatchingUser(CometAddvanced cometAddvanced) {
        cometUserManager.tell(cometAddvanced,cometUserManager);
    }
    
    //最新コメントをリストに追加する
    private void AddCommentList(Comment comment){
        commentList.add(comment);
    }
   
    //視聴者にコメントを配信する
    public void BroadcastComment(Comment newComment){
        cometUserManager.tell(newComment,cometUserManager);
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
        return this.getChannelURI().compareTo(that.getChannelURI());
    }

    
}
