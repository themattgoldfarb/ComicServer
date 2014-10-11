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

    public static Result Library(){
        LibraryViewModel library = getLibraryViewModel();
        Gson gson = new Gson();
        String json = gson.toJson(library.books);
        return ok(json);
    }

    public static Result LibraryBook(int comicBookId){
        ZipFileReader f = new ZipFileReader();
        ComicBook cb = ComicBook.find.byId(comicBookId);
        if(cb.numPages == null){
            cb.numPages = f.NumPages(cb.path, cb.fileName);
        }
        ComicBookViewModel cbvm = new ComicBookViewModel(cb);
        Gson gson = new Gson();
        String json = gson.toJson(cbvm);
        return ok(json);
    }


    public static Result libraryOld(){

        ComicBooks cb = new ComicBooks();
        cb.books = ComicBook.find.all();
        return ok(library.render(cb));
    }




    private static LibraryViewModel getLibraryViewModel(){
        ZipFileReader f = new ZipFileReader();
        ComicBooks cb = new ComicBooks();
        cb.books = ComicBook.find.all();
        for(ComicBook book: cb.books){
            if(book.numPages == null){
                book.numPages = f.NumPages(book.path, book.fileName);
            }
        }
        LibraryViewModel library = new LibraryViewModel(cb);
        return library;
    }

    public static Result app()
    {
        LibraryViewModel library = getLibraryViewModel();
        Gson gson = new Gson();
        String json = gson.toJson(library);
        return ok(app.render(json));
    }

    public static Result login(){
        return ok(
                login.render()
        );

    }
}

