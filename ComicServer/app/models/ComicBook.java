package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by matt on 6/14/14.
 */

@Entity
public class ComicBook extends Model {

    public ComicBook (String fileName, String path,
                      int coverIndex, Integer numPages,
                      String seriesName, int issueNumber){
        this.fileName = fileName;
        this.path = path;
        this.coverIndex = coverIndex;
        this.numPages = numPages;
        this.seriesName = seriesName;
        this.issueNumber = issueNumber;
    }

    public ComicBook(){

    }

    @Id
    public int id;

    public String fileName;

    public String path;

    public int coverIndex;

    public Integer numPages;

    public String seriesName;

    public int issueNumber;

    public static Finder<Integer, ComicBook> find = new Finder<Integer, ComicBook>(
            Integer.class, ComicBook.class
    );
}
