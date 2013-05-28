package models;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.specs2.internal.scalaz.std.java.util.map;

public class CommentTest {

    
    @Test
    public void コメントの作成チェック(){
        Comment comment = new Comment("testName", "testContext", "test tag", "fujimotoChannel");
        assertThat(comment.getUserName()).isEqualTo("testName");
        assertThat(comment.getContext()).isEqualTo("testContext");
        assertThat(comment.getTagString()).isEqualTo("test tag");
        assertThat(comment.getChannelURI()).isEqualTo("fujimotoChannel");
    }
    
    @Test
    public void ハッシュマップのコレクションにちゃんと入ってるか(){
        //stream1に３つ追加
        Comment comment = new Comment("name1", "contect", "tag1", "stream1");
        CommentCenter.AddComment(comment);
        comment = new Comment("name2", "contect", "tag1", "stream1");
        CommentCenter.AddComment(comment);
        comment = new Comment("name3", "contect", "tag1", "stream1");
        CommentCenter.AddComment(comment);
        
        //stream2に2つ
        comment = new Comment("name1", "contect", "tag1", "stream2");
        CommentCenter.AddComment(comment);
        comment = new Comment("name2", "contect", "tag1", "stream2");
        CommentCenter.AddComment(comment);
        
        //stream3に1つ
        comment = new Comment("name1", "contect", "tag1", "stream3");
        CommentCenter.AddComment(comment);
        
        HashMap<String, List<Comment>> map = CommentCenter.commentListMap;
        
        // keyの数は３つのはず
        assertThat(map.keySet().size()).isEqualTo(3);
        
        //それぞのれkeyの中のコレクションの長さをみる
        assertThat(map.get("stream1").size()).isEqualTo(3);
        assertThat(map.get("stream2").size()).isEqualTo(2);
        assertThat(map.get("stream3").size()).isEqualTo(1);
        
        //順番通りになっているか
        List<Comment> list = map.get("stream1");
        assertThat(list.get(0).getUserName()).isEqualTo("name1");
        assertThat(list.get(1).getUserName()).isEqualTo("name2");
        assertThat(list.get(2).getUserName()).isEqualTo("name3");
    }
    
    
    @Test
    public void ハッシュマップがちゃんと作動しているか(){
        Set<String> set= CommentCenter.commentListMap.keySet();
        
        Comment comment = new Comment("name1", "contect1", "tag1", "stream1");
        CommentCenter.AddComment(comment);
       assertThat(set.contains("stream1")).isEqualTo(true);
       assertThat(set.size()).isEqualTo(1);
       
       comment = new Comment("name2", "contect2", "tag2", "stream2");
       CommentCenter.AddComment(comment);
       assertThat(set.contains("stream2")).isEqualTo(true);
       assertThat(set.size()).isEqualTo(2);
       
       comment = new Comment("name3", "contect3", "tag3", "stream2");
       CommentCenter.AddComment(comment);
       assertThat(set.size()).isEqualTo(2);
    }
    
    @Before
    public void Reset(){
        CommentCenter.commentListMap.clear();
    }
    
}
