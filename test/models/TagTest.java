package models;
import static org.fest.assertions.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import views.html.newChannel;

public class TagTest {
    
    @Test
    public void デフォルトタグが作れるか(){
        CommentTag tag = new CommentTag("");
        
        assertThat(tag.getTagColor()).as
        
    }
}
