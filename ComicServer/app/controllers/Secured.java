package controllers;

import models.User;
import models.UserInRole;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matt on 12/14/14.
 */
public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Http.Context ctx){
        return ctx.session().get("email");
    }

    @Override
    public Result onUnauthorized(Http.Context ctx){
        return redirect(routes.Application.login());
    }

    public static List<String> getRoles(){
        String username = Http.Context.current().request().username();
        List<UserInRole> userInRoles = User.find.fetch("userInRoles")
                .where()
                .ilike("email", username)
                .findList()
                .get(0)
                .userInRoles;
        List<String> roles = new ArrayList<>();
        for(UserInRole uir : userInRoles){
            roles.add(uir.roleName);
        }
        return roles;
    }

    public static boolean hasRole(String roleName){
        List<String> roles = getRoles();
        return roles.contains(roleName);
    }

}
