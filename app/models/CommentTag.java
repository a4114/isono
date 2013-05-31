package models;


public class CommentTag {
    
    public enum TagColor{
        Red,Gree,Blue,Pink,Orange,White,Black,Yellow,Cyan,Purple
    }
    public enum TagSize{
        Big,Normal,Small
    }
    public enum TagPosition{
        Ue,Shita,Normal
    }
    
    private  TagColor tagColor; 
    private  TagSize tagSize = TagSize.Normal;
    private  TagPosition tagPosition = TagPosition.Normal;
    private  String tagString;
    
    public CommentTag(TagColor tagColor,TagSize tagSize,TagPosition tagPosition) {
        this.tagColor = tagColor;
        this.tagPosition = tagPosition;
        this.tagSize = tagSize;
        tagString="";
    }
    
    //色タグを初期化（既に初期化済みなら上書きしない）
    private void setTagColor(TagColor color){
        if(this.tagColor == null){this.tagColor = color;}
    }
  
    private void setTagSize(TagSize size){
        if(this.tagSize==TagSize.Normal){this.tagSize = size;}
    }
    
    private void setTagPosition(TagPosition pos){
        if(this.tagPosition == TagPosition.Normal){this.tagPosition=pos;}
    }

    //tagの生文字列を元に各要素を初期化
    public CommentTag(String tagString){
        this.tagString = tagString;
        
        String[] str = tagString.split(" ");
        for(String s : str){
            switch(s){
            case "pink":
                setTagColor(TagColor.Pink);
               break;
            case "orange":
                setTagColor(TagColor.Orange);
                break;
            case "black":
                setTagColor(TagColor.Black);
                break;
            case "white":
                setTagColor(TagColor.White);
                break;
            case "yellow":
                setTagColor(TagColor.Yellow);
                break;
            case "blue":
                setTagColor(TagColor.Blue);
                break;
            case "red":
                setTagColor(TagColor.Red);
                break;
            case "green":
                setTagColor(TagColor.Gree);
                break;
            case "cyan":
                setTagColor(TagColor.Cyan);
                break;
            case "purple":
                setTagColor(TagColor.Purple);
                break;
                
            case "small":
                setTagSize(TagSize.Small);
                break;
            case "big":
                setTagSize(TagSize.Big);
                break;
            
            case "ue":
                setTagPosition(TagPosition.Ue);
                break;
                
            case "shita":
                setTagPosition(TagPosition.Shita);
                break;
              
            }       
        }
        
    }
    
    

    public TagColor getTagColor() {
        return tagColor;
    }

    public TagSize getTagSize() {
        return tagSize;
    }

    public TagPosition getTagPosition() {
        return tagPosition;
    }

    public String getTagString() {
        return tagString;
    }
    
}
