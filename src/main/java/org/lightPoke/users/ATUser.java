package org.lightPoke.users;

public class ATUser extends User {
    public ATUser(final String username, final String password, final int role) {
        super(username, password, role);
    }

    @Override
    public String getUsername() {
        return super.username;
    }

    @Override
    public String getPassword() {
        return super.password;
    }

    @Override
    public int getRole() {
        return super.role;
    }
}
