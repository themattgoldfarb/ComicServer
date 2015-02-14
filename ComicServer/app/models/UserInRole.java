package models;

import play.db.ebean.Model;

import javax.persistence.*;

/**
 * Created by matt on 12/14/14.
 */
@Entity
public class UserInRole extends Model {
    public String email;
    public String roleName;

    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name="email")
    public User user;

    //@ManyToOne
    //public Role role;

    public UserInRole(String email, String roleName){
        this.email = email;
        this.roleName = roleName;
    }
}
