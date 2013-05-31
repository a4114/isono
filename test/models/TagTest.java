package models;

import static org.fest.assertions.Assertions.assertThat;
import models.CommentTag.TagColor;
import models.CommentTag.TagPosition;
import models.CommentTag.TagSize;

import org.junit.Test;

import views.html.newChannel;

public class TagTest {

    @Test
    public void デフォルトタグが作れるか() {
        CommentTag tag = new CommentTag("");

        assertThat(tag.getTagColor()).isEqualTo(TagColor.White);
        assertThat(tag.getTagPosition()).isEqualTo(TagPosition.normal);
        assertThat(tag.getTagSize()).isEqualTo(TagSize.medium);
        
      
        
    }

    @Test
    public void 各色のタグテスト() {
        {
            CommentTag tag = new CommentTag("red");
            assertThat(tag.getTagColor()).isEqualTo(TagColor.Red);
        }
        {
            CommentTag tag = new CommentTag("pink");
            assertThat(tag.getTagColor()).isEqualTo(TagColor.Pink);
        }
        {
            CommentTag tag = new CommentTag("orange");
            assertThat(tag.getTagColor()).isEqualTo(TagColor.Orange);
        }
        {
            CommentTag tag = new CommentTag("black");
            assertThat(tag.getTagColor()).isEqualTo(TagColor.Black);
        }
        {
            CommentTag tag = new CommentTag("white");
            assertThat(tag.getTagColor()).isEqualTo(TagColor.White);
        }
        {
            CommentTag tag = new CommentTag("yellow");
            assertThat(tag.getTagColor()).isEqualTo(TagColor.Yellow);
        }
        {
            CommentTag tag = new CommentTag("blue");
            assertThat(tag.getTagColor()).isEqualTo(TagColor.Blue);
        }
        {
            CommentTag tag = new CommentTag("green");
            assertThat(tag.getTagColor()).isEqualTo(TagColor.Green);
        }
        {
            CommentTag tag = new CommentTag("cyan");
            assertThat(tag.getTagColor()).isEqualTo(TagColor.Cyan);
        }
        {
            CommentTag tag = new CommentTag("purple");
            assertThat(tag.getTagColor()).isEqualTo(TagColor.Purple);
        }
    }
    
    @Test
    public void タグの色の優先度確認(){
        {
            CommentTag tag = new CommentTag("yellow red blue");
            assertThat(tag.getTagColor()).isEqualTo(TagColor.Yellow);
        }
        {
            CommentTag tag = new CommentTag("green yellow red blue");
            assertThat(tag.getTagColor()).isEqualTo(TagColor.Green);
        }
    }
    
    @Test
    public void サイズのテスト(){
        {
            CommentTag tag = new CommentTag("");
            assertThat(tag.getTagSize()).isEqualTo(TagSize.medium);
        }
        {
            CommentTag tag = new CommentTag("large");
            assertThat(tag.getTagSize()).isEqualTo(TagSize.large);
        }
        {
            CommentTag tag = new CommentTag("small");
            assertThat(tag.getTagSize()).isEqualTo(TagSize.small);
        }
        
        {
            CommentTag tag = new CommentTag("small large");
            assertThat(tag.getTagSize()).isEqualTo(TagSize.small);
        }
        
    }

    @Test
    public void ポジションのチェック(){
        {
            CommentTag tag = new CommentTag("ue");
            assertThat(tag.getTagPosition()).isEqualTo(TagPosition.ue);
        }
        {
            CommentTag tag = new CommentTag("shita");
            assertThat(tag.getTagPosition()).isEqualTo(TagPosition.shita);
        }
        {
            CommentTag tag = new CommentTag("");
            assertThat(tag.getTagPosition()).isEqualTo(TagPosition.normal);
        }
    }

    @Test
    public void 全体チェック(){
        
        CommentTag tag = new CommentTag("ue red blue shita green small large");
        assertThat(tag.getTagPosition()).isEqualTo(TagPosition.ue);
        assertThat(tag.getTagColor()).isEqualTo(TagColor.Red);
        assertThat(tag.getTagSize()).isEqualTo(TagSize.small);

        
    }
    
    
    
}
