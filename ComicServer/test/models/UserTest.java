package models;

import org.junit.Before;
import org.junit.Test;
import play.test.WithApplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;

/**
 * Created by matt on 12/14/14.
 */
public class UserTest extends WithApplication{
    @Before
    public  void setUp(){
        start(fakeApplication(inMemoryDatabase()));
    }

    @Test
    public void createAndRetrieveUser() {
        new User("testuser@test.com", "Test", "password").save();

        User testUser = User.find.where().eq("email", "testuser@test.com").findUnique();
        assertNotNull(testUser);
        assertEquals("Test2", testUser.name);
        assertEquals("password", testUser.password);

    }
}
