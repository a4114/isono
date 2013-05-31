package models;

import java.sql.Timestamp;

import org.codehaus.jackson.JsonNode;

import play.libs.Json;


public final class Comment {

    private final String userName;
    private final String context;
    private final CommentTag tag;
    private final String channelURI;
    private final Timestamp timestamp;

    // falseで非表示
    boolean IsEnabled = true;

    //Getter
    public String getUserName() {
        return userName;
    }
    public String getContext() {
        return context;
    }
    public CommentTag getTag(){return tag;}

    public String getChannelURI() {
        return channelURI;
    }
    public Timestamp getTimestamp() {
        return timestamp;
    }

    //コンストラクタ
    public Comment(String userName, String context, String tagString,
            String channelURI) {
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.userName = userName;
        this.context = context;
        this.tag = new CommentTag(tagString);
        this.channelURI = channelURI;
        CheckNGComment();
    }


    // NGコメントちぇっく
    private void CheckNGComment() {
        System.out.println("[CommentServer]-[CheckNGComment]:実装してない");

        return;
    }
    
    //Jsonに変換
    public JsonNode toJson(){
        return Json.toJson(this);
    }

}
