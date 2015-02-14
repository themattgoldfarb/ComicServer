package ViewModels;

import models.ComicBook;
import models.User;
import models.UserInRole;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matt on 6/14/14.
 */
public class UserViewModel {

    public UserViewModel(User u){
        email = u.email;
        name = u.name;
        password = u.password;
        roles=new ArrayList<String>();
        for(UserInRole uir : u.userInRoles){
            roles.add(uir.roleName);
        }
    }

    public static List<UserViewModel> GetViewModels(List<User> users){
        ArrayList<UserViewModel> viewModels = new ArrayList<UserViewModel>();
        for(User u : users){
            viewModels.add(new UserViewModel(u));
        }
        return viewModels;
    }

    public String email;
    public String name;
    public String password;
    public List<String> roles;
}
