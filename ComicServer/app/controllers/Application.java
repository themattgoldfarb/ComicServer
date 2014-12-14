package controllers;

import ViewModels.LibraryViewModel;
import play.api.Configuration;
import play.api.Play;
import play.data.Form;
import play.mvc.*;
import views.html.*;

import AppCode.*;
import com.google.gson.Gson;

import static play.data.Form.form;


public class Application extends Controller {

    public static class Login {

        public String email;
        public String password;



    }


    public static Result login(){
        return ok (login.render(form(Login.class)));
    }

    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        return ok();
    }

    public static Result app()
    {
        LibraryReader libraryReader = new LibraryReader();
        LibraryViewModel library = libraryReader.getLibraryViewModel();
        Gson gson = new Gson();
        String json = gson.toJson(library);
        return ok(app.render(json));
    }

}

