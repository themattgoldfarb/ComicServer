package controllers;

import ViewModels.ComicBookViewModel;
import models.ComicBooks;
import play.mvc.*;
import views.html.*;

import java.io.*;

import models.*;
import AppCode.*;
import com.google.gson.Gson;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
    
    public static Result read() {
    	return ok(read.render());
    	
    }
    
    
    public static Result reader(int comicBookId ){
    	ZipFileReader f = new ZipFileReader();
        ComicBook cb = ComicBook.find.byId(comicBookId);
        if(cb.numPages == null){
            cb.numPages = f.NumPages(cb.path, cb.fileName);
        }
        ComicBookViewModel cbvm = new ComicBookViewModel(cb);
    	Gson gson = new Gson();
    	String json = gson.toJson(cbvm);
    	return ok(reader.render(json));
    }
    
    public static Result library(){
        ComicBooks cb = new ComicBooks();
        cb.books = ComicBook.find.all();
        return ok(library.render(cb));
    }
    
    public static Result page(int comicBookId, int pageId){
        ZipFileReader f = new ZipFileReader();
        ComicBook cb = ComicBook.find.byId(comicBookId);
        InputStream is = f.GetPage(cb.path, cb.fileName, pageId);
        return ok(is);
    }
}

