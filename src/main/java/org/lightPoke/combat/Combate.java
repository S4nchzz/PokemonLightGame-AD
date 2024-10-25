package org.lightPoke.combat;

import org.lightPoke.tournament.Tournament;

import java.time.LocalDate;

public class Combate {
    private final long id;
    private final Tournament torneo;
    private final LocalDate fecha;

    public Combate(long id, Tournament torneo, LocalDate fecha) {
        this.id = id;
        this.torneo = torneo;
        this.fecha = fecha;
    }
}
