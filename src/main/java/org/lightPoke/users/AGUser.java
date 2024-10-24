package org.lightPoke.users;

public class AGUser extends User {
    public AGUser(final String username, final String password, final int role) {
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
