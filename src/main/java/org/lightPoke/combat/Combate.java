package org.lightPoke.combat;

import org.lightPoke.tournament.Torneo;

import java.time.LocalDate;

public class Combate {
    private final long id;
    private final Torneo torneo;
    private final LocalDate fecha;

    public Combate(long id, Torneo torneo, LocalDate fecha) {
        this.id = id;
        this.torneo = torneo;
        this.fecha = fecha;
    }
}
