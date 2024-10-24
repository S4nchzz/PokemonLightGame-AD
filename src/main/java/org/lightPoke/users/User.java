package org.lightPoke.users;

public abstract class User {
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
