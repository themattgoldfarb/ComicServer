package controllers;

import AppCode.ThumbnailReader;
import AppCode.ZipFileReader;
import ViewModels.ComicBookViewModel;
import com.google.gson.Gson;
import models.ComicBook;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;


/**
 * Created by matt on 10/3/14.
 */
public class PageReader extends Controller {

    @Security.Authenticated(Secured.class)
    public static Result page(int comicBookId, int pageId) {
        ZipFileReader f = new ZipFileReader();
        ComicBook cb = ComicBook.find.byId(comicBookId);
        InputStream is = f.GetPage(cb.path, cb.fileName, pageId);
        return ok(is).as("image/jpeg");
    }

    @Security.Authenticated(Secured.class)
    public static Result thumbnail(int comicBookId) {
        ZipFileReader f = new ZipFileReader();
        ThumbnailReader thumbnailReader = new ThumbnailReader();
        InputStream is = thumbnailReader.getThumbnail(comicBookId);
        if(is == null) {
            ComicBook cb = ComicBook.find.byId(comicBookId);
            is = f.GetPage(cb.path, cb.fileName, cb.coverIndex);
        }
        return ok(is).as("image/jpeg");
    }



}
