package controllers;

import AppCode.ZipFileReader;
import ViewModels.FilesViewModel;
import com.google.gson.Gson;
import models.ComicBook;
import models.ComicBooks;
import play.libs.F;
import play.mvc.Controller;
import views.html.read;
import play.mvc.Result;

import java.util.HashSet;


/**
 * Created by matt on 10/3/14.
 */
public class FileManager extends Controller {
    public static Result readPath(String path){
        ZipFileReader f = new ZipFileReader();
        FilesViewModel vm = f.readDirectory(path);
        Gson gson = new Gson();
        String json = gson.toJson(vm);
        return ok(json);
    }

    public static Result addPath(String path){
        ZipFileReader f = new ZipFileReader();
        FilesViewModel vm = f.readDirectory(path);
        ComicBooks books = new ComicBooks();
        books.books = ComicBook.find.all();
        HashSet<String> booksSearch = new HashSet<String>();
        for (ComicBook b : books.books){
            booksSearch.add(b.path+b.fileName);
        }
        for(String file : vm.files ){
            ComicBook cb = new ComicBook(
                    file,
                    vm.directory,
                    1,
                    f.NumPages(vm.directory, file),
                    file,
                    1
            );
            if(!booksSearch.contains(cb.path+cb.fileName)) {
                cb.save();
            }
        }

        return ok("{}");
    }
}
