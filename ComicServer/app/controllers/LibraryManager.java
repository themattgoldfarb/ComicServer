package controllers;

import AppCode.LibraryReader;
import AppCode.ZipFileReader;
import ViewModels.ComicBookViewModel;
import ViewModels.LibraryViewModel;
import com.google.gson.Gson;
import models.ComicBook;
import play.mvc.*;

/**
 * Created by matt on 10/11/14.
 */
public class LibraryManager extends Controller{

    public static Result Library(){
        LibraryReader libraryReader = new LibraryReader();
        LibraryViewModel library = libraryReader.getLibraryViewModel();
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
}
