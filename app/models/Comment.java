package models;

import java.sql.Timestamp;

public final class Comment {

    private final String userName;
    private final String context;
    private final String tagString;
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
    public String getTagString() {
        return tagString;
    }
    public String getChannelURI() {
        return channelURI;
    }
    public Timestamp getTimestamp() {
        return timestamp;
    }

    //コンストラクタ
    public Comment(String userName, String context, String tagString,
            String channelName) {
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.userName = userName;
        this.context = context;
        this.tagString = NormalizeTagString(tagString);
        this.channelURI = channelName;
        CheckNGComment();
    }

    // 入力タグコメントを正規化
    private String NormalizeTagString(String original) {

        System.out.println("[CommentServer]-[NormalizeTagString]:実装してない");
        return original;

        /*
         * if(original==null){return "";} String result = "";
         * 
         * boolean setedColor =false; boolean setedFixPosition = false; boolean
         * setedCharactorSize = false;
         * 
         * String[] str=original.split(" "); for(String s : str){
         * 
         * 
         * }
         */
    }

    // NGコメントちぇっく
    private void CheckNGComment() {
        System.out.println("[CommentServer]-[CheckNGComment]:実装してない");

        return;
    }

}
