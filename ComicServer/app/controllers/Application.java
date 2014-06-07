package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
    
    public static Result read() {
    	return ok(read.render());
    	
    }
    
    
    public static Result reader(){
    	
    	return ok(reader.render(""));
    }
    
    public static Result library(){
    	return ok(library.render());
    }

}
