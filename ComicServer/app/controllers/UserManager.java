package controllers;

import AppCode.ThumbnailReader;
import AppCode.ZipFileReader;
import ViewModels.FilesViewModel;
import ViewModels.UserViewModel;
import com.google.gson.Gson;
import models.ComicBook;
import models.ComicBooks;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


/**
 * Created by matt on 10/3/14.
 */
public class UserManager extends Controller {

    @Security.Authenticated(Secured.class)
    public static Result users(){
        List<UserViewModel> users = UserViewModel.GetViewModels(User.find.fetch("userInRoles").findList());
        Gson gson = new Gson();
        return ok(gson.toJson(users));

    }
}
