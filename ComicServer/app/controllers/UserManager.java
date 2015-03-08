package controllers;

import ViewModels.UserViewModel;
import com.google.gson.Gson;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.createUser;

import java.util.List;

import static play.data.Form.form;


/**
 * Created by matt on 10/3/14.
 */
public class UserManager extends Controller {

    public static class NewUser {
        public String email;
        public String name;
        public String password;
    }



    @Security.Authenticated(Secured.class)
    public static Result users(){
        if(Secured.hasRole("userAdministrator")) {
            List<UserViewModel> users = UserViewModel.GetViewModels(User.find.fetch("userInRoles").findList());
            Gson gson = new Gson();
            return ok(gson.toJson(users));
        }
        else{
            return forbidden();
        }
    }

    @Security.Authenticated(Secured.class)
    public static Result createUser(){
        if(Secured.hasRole("userAdministrator")) {
            return ok(createUser.render(form(NewUser.class)));
        }
        else{
            return forbidden();
        }
    }

    @Security.Authenticated(Secured.class)
    public static Result addUser(){
        if(Secured.hasRole("userAdministrator")) {
            Form<NewUser> newUserForm = form(NewUser.class).bindFromRequest();
            if (newUserForm.hasErrors()) {
                return badRequest(createUser.render(newUserForm));
            } else {
                User u = new User(
                        newUserForm.get().email,
                        newUserForm.get().name,
                        newUserForm.get().password);
                u.save();
                return redirect(
                        routes.UserManager.users()
                );
            }
        }
        else{
            return forbidden();
        }
    }
}
