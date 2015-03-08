package controllers;

import AppCode.ThumbnailReader;
import AppCode.ZipFileReader;
import ViewModels.FilesViewModel;
import com.google.gson.Gson;
import models.ComicBook;
import models.ComicBooks;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.HashSet;


/**
 * Created by matt on 10/3/14.
 */
public class FileManager extends Controller {

    @Security.Authenticated(Secured.class)
    public static Result readPath(String path){
        if(Secured.hasRole("fileAdministrator")) {
            ZipFileReader f = new ZipFileReader();
            FilesViewModel vm = f.readDirectory(path);
            Gson gson = new Gson();
            String json = gson.toJson(vm);
            return ok(json);
        }
        else{
            return forbidden();
        }
    }

    @Security.Authenticated(Secured.class)
    public static Result rescan(){
        if(Secured.hasRole("fileAdministrator")) {
            ComicBooks books = new ComicBooks();
            books.books = ComicBook.find.all();
            ZipFileReader reader = new ZipFileReader();
            for( ComicBook b : books.books){
                if(!reader.bookExists(b.path, b.fileName)){
                    b.delete();
                }
            }
            return redirect(routes.Application.app());
        }
        else{
            return forbidden();
        }
    }

    @Security.Authenticated(Secured.class)
    public static Result addPath(String path){
        if(Secured.hasRole("fileAdministrator")) {
            ZipFileReader f = new ZipFileReader();
            FilesViewModel vm = f.readDirectory(path);
            ComicBooks books = new ComicBooks();
            books.books = ComicBook.find.all();
            HashSet<String> booksSearch = new HashSet<String>();
            for (ComicBook b : books.books) {
                booksSearch.add(b.path + b.fileName);
            }
            for (String file : vm.files) {
                ComicBook cb = new ComicBook(
                        file,
                        vm.directory,
                        1,
                        f.NumPages(vm.directory, file),
                        file,
                        1
                );
                if (!booksSearch.contains(cb.path + cb.fileName)) {
                    cb.save();
                }
                ThumbnailReader thumbnailReader = new ThumbnailReader();
                thumbnailReader.saveThumbnail(cb.id, 1);
            }

            return ok("{}");
        }
        else{
            return forbidden();
        }
    }
}
