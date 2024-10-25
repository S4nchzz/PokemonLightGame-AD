package org.lightPoke.trainerLicense;

import java.time.LocalDate;

public class License {
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
}
