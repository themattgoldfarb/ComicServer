package controllers;

import ViewModels.ComicBookViewModel;
import ViewModels.FilesViewModel;
import ViewModels.LibraryViewModel;
import models.ComicBooks;
import play.mvc.*;
import views.html.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import models.*;
import AppCode.*;
import com.google.gson.Gson;
import views.html.read;

import javax.imageio.ImageIO;


public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
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

