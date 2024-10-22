package org.lightPoke.auth;

public class UserData {
    private final String username;
    private final String password;
    private final int role;

    public UserData(String username, String password, int role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getRole() {
        return role;
    }
}
