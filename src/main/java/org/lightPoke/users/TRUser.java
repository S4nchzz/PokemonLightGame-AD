package org.lightPoke.users;

import org.lightPoke.trainerLicense.Carnet;

import java.time.LocalDate;

public class TRUser extends User{
    private long id;
    private Carnet carnet;
    private String nombre;
    private String nacionalidad;

    public TRUser(final String username, final String password, final int role) {
        super(username, password, role);
    }

    public void generateInfo(long id, String nombre, String nacionalidad) {
        this.id = id;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;

        this.carnet = new Carnet(id, LocalDate.now(), 0, 0);
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

    public Carnet getCarnet() {
        return carnet;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }
}