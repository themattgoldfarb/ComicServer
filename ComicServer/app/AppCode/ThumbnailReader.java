package AppCode;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import models.ComicBook;
import org.imgscalr.Scalr;
import sun.misc.IOUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;


/**
 * Created by matt on 10/11/14.
 */
public class ThumbnailReader {

    public String saveThumbnail(int comicBookId, int pageId){
        ZipFileReader f = new ZipFileReader();
        ComicBook cb = ComicBook.find.byId(comicBookId);
        InputStream is = f.GetPage(cb.path, cb.fileName, pageId);
        BufferedImage img = null;
        try{
            img = ImageIO.read(is);
        } catch (IOException e){

        }
        BufferedImage thumbnail = Scalr.resize(img, 200);
        String fileName = getThumbnailFileName(comicBookId);
        File thumbnailFile = new File(fileName);
        try {
            ImageIO.write(thumbnail, "jpg", thumbnailFile);
        } catch (IOException e){

        }
        return fileName;
    }

    private String getThumbnailFileName(int comicBookId){
        Config conf = ConfigFactory.load();
        String thumbnailPath = conf.getString("thumbnail.path");
        return thumbnailPath+"thumbnail-"+comicBookId+".jpg";
    }

    public InputStream getThumbnail(int comicBookId){
        String fileName = getThumbnailFileName(comicBookId);
        BufferedImage img = null;
        try{
            img = ImageIO.read(new File(fileName));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", baos);
            return new ByteArrayInputStream(baos.toByteArray());
        }
        catch (IOException e){

        }
        return null;
    }




}
