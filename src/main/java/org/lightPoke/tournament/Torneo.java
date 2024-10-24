package org.lightPoke.tournament;

import org.lightPoke.users.TRUser;

import java.util.List;

public class Torneo {
    private final int id;
    private final List<TRUser> trainers;
    private final String nombre;
    private final char codRegion;
    private final float puntosVictoria;

    public Torneo(List<TRUser> trainers, int id, String nombre, char codRegion, float puntosVictoria) {
        this.trainers = trainers;
        this.id = id;
        this.nombre = nombre;
        this.codRegion = codRegion;
        this.puntosVictoria = puntosVictoria;
    }
}
