package org.lightPoke.db.db4o;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.query.Query;
import org.lightPoke.db.db4o.entities.UserEnt_db4o;

import java.util.List;


public class DB4oInstance {
    private static ObjectContainer instance;
    private static final String DATABASE_FILE = "src/main/resources/db/pokemon_lg.db4o";

    private DB4oInstance() {}

    public static ObjectContainer getInstance() {
        if (instance == null) {
            instance = Db4oEmbedded.openFile(DATABASE_FILE);
            storeAGIfNotExists("iyan", "a", "AG");
        }

        return instance;
    }

    private static void storeAGIfNotExists(String username, String password, String type) {
        Query query = instance.query();
        query.constrain(UserEnt_db4o.class);

        query.descend("username").constrain(username);
        List<UserEnt_db4o> users = query.execute();


        if (users.isEmpty()) {
            instance.store(new UserEnt_db4o(username, password, type, 1));
        }
    }
}
