package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by matt on 12/14/14.
 */
@Entity
public class User extends Model {
    @Id
    public String email;
    public String name;
    public String password;

    public User(String email, String name, String password){
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public static User authenticate(String email, String password) {
        return find.where().eq("email", email)
                .eq("password", password).findUnique();
    }

    public static Finder<String,User> find = new Finder<String, User>(String.class, User.class);
}
