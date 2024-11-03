package org.lightPoke.tournament;

import org.lightPoke.users.ATUser;
import org.lightPoke.users.TRUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tournament implements Serializable {
    private final int id;
    private ArrayList<TRUser> trainers;
    private final ATUser adminTournament;
    private final String nombre;
    private final char codRegion;
    private final float puntosVictoria;

    public Tournament(final ATUser adminTournament, int id, String nombre, char codRegion, float puntosVictoria) {
        this.adminTournament = adminTournament;
        this.id = id;
        this.nombre = nombre;
        this.codRegion = codRegion;
        this.puntosVictoria = puntosVictoria;

        trainers = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public ArrayList<TRUser> getTrainers() {
        return trainers;
    }

    public void addTrainer(final TRUser trainer) {
        this.trainers.add(trainer);
    }

    public ATUser getAdminTournament() {
        return this.adminTournament;
    }

    public String getNombre() {
        return nombre;
    }

    public char getCodRegion() {
        return codRegion;
    }

    public float getPuntosVictoria() {
        return puntosVictoria;
    }
}
