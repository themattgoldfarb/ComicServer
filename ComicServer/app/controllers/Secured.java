package controllers;

import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

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

}
