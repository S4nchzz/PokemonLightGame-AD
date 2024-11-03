package org.lightPoke.users;

import java.io.Serializable;

public abstract class User implements Serializable {
    protected final String username;
    protected final String password;
    protected final int role;

    public User(final String username, final String password, final int role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public abstract String getUsername();
    public abstract String getPassword();
    public abstract int getRole();
}
