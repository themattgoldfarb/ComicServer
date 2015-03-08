import com.avaje.ebean.Ebean;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import controllers.Application;
import models.User;
import models.UserInRole;
import play.GlobalSettings;

/**
 * Created by matt on 12/14/14.
 */
public class Global extends GlobalSettings {
    @Override
    public void onStart(play.Application app){
        Config conf = ConfigFactory.load();
        String adminPassword = conf.getString("admin.password");

        if(User.find.findRowCount()==0){
            User admin = new User("admin@admin.com", "admin", adminPassword);
            admin.save();
        }

        User admin = User.find.byId("admin@admin.com");
        if(!admin.getRoles().contains("fileAdministrator")){
            UserInRole uir = new UserInRole("admin@admin.com", "fileAdministrator");
            uir.save();
        }
        if(!admin.getRoles().contains("userAdministrator")){
            UserInRole uir = new UserInRole("admin@admin.com", "userAdministrator");
            uir.save();
        }
    }
}
