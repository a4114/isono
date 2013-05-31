import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;
import views.html.watch;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest {

    @Test 
    public void simpleCheck() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
    }
    
    @Test
    public void renderTemplate() {
      //  Content html = views.html.index.render("Your new application is ready.");
      //  assertThat(contentType(html)).isEqualTo("text/html");
      //  assertThat(contentAsString(html)).contains("Your new application is ready.");
    }
    
    @Test
    public void broadcastRTMPStreamName() {
        String channelURI = "lv1234";
        Result result = controllers.Application.broadcast(channelURI);
        assertThat(contentAsString(result)).contains("Stream：" + channelURI);
    }
    
    @Test
    public void watchVideoURL() {
        String channelURI = "lv1234";
        Content html = views.html.watch.render(channelURI);
        assertThat(contentAsString(html)).contains("http://nc07.ff-in.dwango.co.jp:8080/hls/" + channelURI + ".m3u8");
    }
    
    
}
