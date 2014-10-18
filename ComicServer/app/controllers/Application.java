package controllers;

import ViewModels.LibraryViewModel;
import play.api.Configuration;
import play.api.Play;
import play.mvc.*;
import views.html.*;

import AppCode.*;
import com.google.gson.Gson;


public class Application extends Controller {

    public static Result app()
    {
        LibraryReader libraryReader = new LibraryReader();
        LibraryViewModel library = libraryReader.getLibraryViewModel();
        Gson gson = new Gson();
        String json = gson.toJson(library);
        return ok(app.render(json));
    }

}

