package controllers;

import models.Comment;
import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
  
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
    
    public static void UpdateComment(Comment comment){
        
    }
  
}
