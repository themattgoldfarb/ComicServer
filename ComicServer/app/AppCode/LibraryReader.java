package AppCode;

import ViewModels.LibraryViewModel;
import models.ComicBook;
import models.ComicBooks;

/**
 * Created by matt on 10/11/14.
 */
public class LibraryReader {
    public static LibraryViewModel getLibraryViewModel(){
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
}
