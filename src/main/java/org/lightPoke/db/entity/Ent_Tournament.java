package org.lightPoke.db.entity;

import jakarta.persistence.*;

@Entity(name = "pkm_tournament")
@Table(name = "pkm_tournament")
public class Ent_Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "region")
    private char region;

    @Column(name = "victoryPoints")
    private float victoryPoints;

    @OneToOne
    @JoinColumn(name = "t_winner")
    private Ent_Trainer t_winner;

    public Ent_Tournament(String name, char region, float victoryPoints) {
        this.name = name;
        this.region = region;
        this.victoryPoints = victoryPoints;
    }

    public Ent_Tournament(int id, String name, char region, float victoryPoints, Ent_Trainer t_winner) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.victoryPoints = victoryPoints;
        this.t_winner = t_winner;
    }

    public Ent_Tournament(String name, char region) {
        this.name = name;
        this.region = region;
    }

    public Ent_Tournament() {}

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public char getRegion() {
        return region;
    }

    public float getVictoryPoints() {
        return victoryPoints;
    }

    public Ent_Trainer getTWinner() {
        return this.t_winner;
    }
}