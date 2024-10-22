package org.lightPoke.trainer;

import org.lightPoke.trainerLicense.Carnet;

public class Entrenador {
    private final long id;
    private final Carnet carnet;
    private final String nombre;
    private final String nacionalidad;

    public Entrenador(Carnet carnet, long id, String nombre, String nacionalidad) {
        this.carnet = carnet;
        this.id = id;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
    }
}
