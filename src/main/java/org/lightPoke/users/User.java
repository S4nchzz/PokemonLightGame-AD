package org.lightPoke.users;

import java.io.Serializable;

/**
 * Interfaz que contiene los valores basicos
 * que contiene un usuario
 *
 * @author Iyan Sanchez da Costa
 */
public abstract class User implements Serializable {
    protected final String username;
    protected final String password;
    protected final int role; // 1 -> Trainer // 2 -> Admin tournament // 3 -> General Admin

    public User(final String username, final String password, final int role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public abstract String getUsername();
    public abstract String getPassword();
    public abstract int getRole();
}
