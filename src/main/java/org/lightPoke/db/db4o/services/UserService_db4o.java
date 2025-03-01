package org.lightPoke.db.db4o.services;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;
import org.lightPoke.db.db4o.DB4oInstance;
import org.lightPoke.db.db4o.entities.UserEnt_db4o;

import java.util.List;

public class UserService_db4o {
    private final ObjectContainer db;

    public UserService_db4o() {
        this.db = DB4oInstance.getInstance();
    }

    public void save(UserEnt_db4o trainerEntDb4o) {
        db.store(trainerEntDb4o);
    }

    public List<UserEnt_db4o> findAll() {
        Query query = db.query();
        query.constrain(UserEnt_db4o.class);
        List<UserEnt_db4o> trainers = query.execute();

        return trainers.isEmpty() ? null : trainers;
    }

    public UserEnt_db4o findByCredentials(final String username, final String password) {
        Query query = db.query();
        query.constrain(UserEnt_db4o.class);

        query.descend("username").constrain(username).equal();
        query.descend("password").constrain(password).equal();

        List<UserEnt_db4o> trainers = query.execute();

        return trainers.isEmpty() ? null : trainers.getFirst();
    }

    public UserEnt_db4o findByUsername(final String username) {
        Query query = db.query();
        query.constrain(UserEnt_db4o.class);

        query.descend("username").constrain(username).equal();
        List<UserEnt_db4o> users = query.execute();

        return users.isEmpty() ? null : users.getFirst();
    }
}
