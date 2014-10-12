package controllers;

import AppCode.ZipFileReader;
import ViewModels.ComicBookViewModel;
import com.google.gson.Gson;
import models.ComicBook;
import play.mvc.Controller;
import play.mvc.Result;

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
    public static Result thumbnail(int comicBookId, int pageId) {
        ZipFileReader f = new ZipFileReader();
        ComicBook cb = ComicBook.find.byId(comicBookId);
        InputStream is = f.GetPage(cb.path, cb.fileName, pageId);
        InputStream fis = resize(is, 200, 200);
        return ok(fis);
    }

    private static InputStream resize(InputStream is, int maxWidth, int maxHeight){
        try{
            BufferedImage i = ImageIO.read(is);
            int height = i.getHeight();
            int width = i.getWidth();
            int scaleFactor = 1;
            if(height/maxHeight>width/maxWidth) {
                scaleFactor = height / maxHeight;
            }
            else {
                scaleFactor = width / maxWidth;
            }
            height = height/scaleFactor;
            width = width/scaleFactor;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(i,"jpg", baos);
            InputStream mis = new ByteArrayInputStream(baos.toByteArray());
            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            img.createGraphics()
                    .drawImage(ImageIO.read(mis)
                            .getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH),0,0,null);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(img,"jpg", os);
            InputStream fis = new ByteArrayInputStream(os.toByteArray());
            return fis;
        } catch (Exception e){

        }

        return null;
    }

    public static Result page(int comicBookId, int pageId) {
        ZipFileReader f = new ZipFileReader();
        ComicBook cb = ComicBook.find.byId(comicBookId);
        InputStream is = f.GetPage(cb.path, cb.fileName, pageId);
        return ok(is);
    }


}
