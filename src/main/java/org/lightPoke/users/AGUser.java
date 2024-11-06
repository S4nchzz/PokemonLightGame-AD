package org.lightPoke.users;

/**
 * Clase que extiende de USER y se instancia a
 * la hora de crear un usuario de tipo
 * Administrador general
 *
 * @author Iyan Sanchez da Costa
 */
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
