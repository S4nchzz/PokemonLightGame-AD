package org.lightPoke.db.db4o.services;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;
import org.lightPoke.db.db4o.DB4oInstance;
import org.lightPoke.db.db4o.entities.UserEnt_db4o;
import org.lightPoke.db.services.Svice_Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService_db4o {
    private final ObjectContainer db;

    @Autowired
    private Svice_Trainer sviceTrainer;

    public UserService_db4o() {
        this.db = DB4oInstance.getInstance();
    }

    public void save(UserEnt_db4o trainerEntDb4o) {
        db.store(trainerEntDb4o);
    }

    public List<UserEnt_db4o> findAll() {
        Query query = db.query();
        query.constrain(UserEnt_db4o.class);
        List<UserEnt_db4o> users = query.execute();

        return users.isEmpty() ? null : users;
    }

    public UserEnt_db4o findByCredentials(final String username, final String password) {
        Query query = db.query();
        query.constrain(UserEnt_db4o.class);

        query.descend("username").constrain(username).equal();
        query.descend("password").constrain(password).equal();

        List<UserEnt_db4o> users = query.execute();

        return users.isEmpty() ? null : users.getFirst();
    }

    public UserEnt_db4o findByUsername(final String username) {
        Query query = db.query();
        query.constrain(UserEnt_db4o.class);

        query.descend("username").constrain(username).equal();
        List<UserEnt_db4o> users = query.execute();

        return users.isEmpty() ? null : users.getFirst();
    }

    public List<UserEnt_db4o> findAllUsers() {
        Query query = db.query();
        query.constrain(UserEnt_db4o.class);

        return query.execute();
    }

    public void removeUser(String username) {
        Query query = db.query();
        query.constrain(UserEnt_db4o.class);

        query.descend("username").constrain(username);
        List<UserEnt_db4o> users = query.execute();

        if (!users.isEmpty()) {
            UserEnt_db4o user = users.getFirst();
            db.delete(user);
        }

        sviceTrainer.removeUserByUsername(username);
    }

    public void modifyUsername(String username, String newUsername) {
        Query query = db.query();
        query.constrain(UserEnt_db4o.class);

        query.descend("username").constrain(username);
        List<UserEnt_db4o> users = query.execute();

        if (!users.isEmpty()) {
            UserEnt_db4o user = users.getFirst();
            user.setUsername(newUsername);

            db.store(user);
        }

        sviceTrainer.changeUsername(username, newUsername);
    }

    public void modifyPassword(String username, String newPassword) {
        Query query = db.query();
        query.constrain(UserEnt_db4o.class);

        query.descend("username").constrain(username);
        List<UserEnt_db4o> users = query.execute();

        if (!users.isEmpty()) {
            UserEnt_db4o user = users.getFirst();
            user.setPassword(newPassword);

            db.store(user);
        }
    }
}
