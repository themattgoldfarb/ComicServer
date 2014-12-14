package controllers;

import ViewModels.LibraryViewModel;
import models.User;
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

        public String validate(){
            if (User.authenticate(email, password) == null){
                return "Invalid user or password";
            }
            return null;
        }

    }


    public static Result login(){
        return ok (login.render(form(Login.class)));
    }

    public static Result logout(){
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
                routes.Application.login()
        );
    }

    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session().clear();
            session("email", loginForm.get().email);
            return redirect(
                    routes.Application.app()
            );
        }
    }

    @Security.Authenticated(Secured.class)
    public static Result app()
    {
        User user = User.find.byId(request().username());
        LibraryReader libraryReader = new LibraryReader();
        LibraryViewModel library = libraryReader.getLibraryViewModel();
        Gson gson = new Gson();
        String json = gson.toJson(library);
        return ok(app.render(json, user.name));
    }

}

