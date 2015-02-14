package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by matt on 12/14/14.
 */
@Entity
public class User extends Model {
    @Id
    public String email;
    public String name;
    public String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    public List<UserInRole> userInRoles = new ArrayList<UserInRole>();

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
