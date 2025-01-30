package org.lightPoke.users;
import java.time.LocalDate;

/**
 * Clase que extiende de USER y se instancia a
 * la hora de crear un usuario de tipo
 * Entrenador
 *
 * @author Iyan Sanchez da Costa
 */
public class TRUser extends User{
    private long id;
    private String nombre;
    private String nacionalidad;

    public TRUser(final String username, final String password, final int role) {
        super(username, password, role);
    }

    public void generateInfo(long id, String nombre, String nacionalidad) {
        this.id = id;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
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

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    @Override
    public String toString() {
        return String.format(
                "TRUser{id=%d, username='%s', nombre='%s', nacionalidad='%s', role=%d}",
                id, getUsername(), nombre, nacionalidad, getRole()
        );
    }
}