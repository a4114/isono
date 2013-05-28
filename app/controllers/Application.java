package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
	  
    public static Result index() {
        return ok(index.render("メインページ"));
    }
    
    public static Result watch(String channelURI) {
        return ok(watch.render(channelURI));
    }
    
    public static Result updateComment(String channelURI) {
        return redirect("/watch/" + channelURI);
    }
    
    public static Result newChannel() {
    	return ok(newChannel.render());
    }

    public static Result updateChannel() {
    	String channelURI = "lv999999";
    	return redirect("/broadcast/" + channelURI);
    }

    
    public static Result broadcast(String channelURI) {
        return ok(broadcast.render(channelURI));
    }
    
    public static Result updateOwnerComment(String channelURI) {
        return redirect("/broadcast/" + channelURI);
    }
    
  
}
