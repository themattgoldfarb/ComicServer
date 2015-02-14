package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by matt on 12/14/14.
 */
@Entity
public class Role extends Model {
    @Id
    public String roleName;

    public Role(String roleName){
        this.roleName = roleName;
    }

    public static Finder<String, Role> find = new Finder<String, Role>(String.class, Role.class);
}
