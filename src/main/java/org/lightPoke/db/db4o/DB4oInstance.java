package org.lightPoke.db.db4o;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import org.lightPoke.db.db4o.entities.UserEnt_db4o;

public class DB4oInstance {
    private static ObjectContainer instance;

    private DB4oInstance() {}

    public static ObjectContainer getInstance() {
        if (instance == null) {
            instance = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "src/main/resources/db/pokemon_lg.db4o");
            instance.store(new UserEnt_db4o("iyan", "a", "AG", 1));
        }

        return instance;
    }
}
