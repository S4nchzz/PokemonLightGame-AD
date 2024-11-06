package org.lightPoke.trainerLicense;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Clase serializada que contiene todos los datos que
 * puede tener un usuario entrenador (TRUser)
 *
 * @author Iyan Sanchez da Costa
 */
public class License implements Serializable {
    private final long idEntrenador;
    private final LocalDate fechaExpedicion;
    private final float puntos;
    private final int numVictorias;

    public License(long idEntrenador, LocalDate fechaExpedicion, float puntos, int numVictorias) {
        this.idEntrenador = idEntrenador;
        this.fechaExpedicion = fechaExpedicion;
        this.puntos = puntos;
        this.numVictorias = numVictorias;
    }

    public long getIdEntrenador() {
        return idEntrenador;
    }

    public String getFechaExpedicion() {
        return fechaExpedicion.getDayOfMonth() + "/" + fechaExpedicion.getMonthValue() + "/" + fechaExpedicion.getYear();
    }

    public float getPuntos() {
        return puntos;
    }

    public int getNumVictorias() {
        return numVictorias;
    }
}
