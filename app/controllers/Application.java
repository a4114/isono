package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
	  
    public static Result index() {
        return ok(index.render("メインページ"));
    }
    
    public static Result watch(String chURI) {
        return ok(watch.render(chURI));
    }
    
    public static Result updateComment(String chURI) {
        return redirect("/watch/" + chURI);
    }
  
}
