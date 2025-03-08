package org.lightPoke.db.mysql.entity;

import jakarta.persistence.*;

@Entity(name = "pkm_combat")
@Table(name = "pkm_combat")
public class Ent_Combat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date")
    private String date;

    @ManyToOne
    @JoinColumn(name = "tournament", nullable = false)
    private Ent_Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "trainer_1")
    private Ent_Trainer trainer_1;

    @ManyToOne
    @JoinColumn(name = "trainer_2")
    private Ent_Trainer trainer_2;

    @ManyToOne
    @JoinColumn(name = "c_winner")
    private Ent_Trainer c_winner;

    public Ent_Combat(int id, String date, Ent_Tournament tournament, Ent_Trainer trainer1, Ent_Trainer trainer2, Ent_Trainer cWinner) {
        this.id = id;
        this.date = date;
        this.tournament = tournament;
        this.trainer_1 = trainer1;
        this.trainer_2 = trainer2;
        this.c_winner = cWinner;
    }

    public Ent_Combat(Ent_Tournament tournament) {
        this.tournament = tournament;
    }

    public Ent_Combat() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Ent_Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Ent_Tournament tournament) {
        this.tournament = tournament;
    }

    public Ent_Trainer getTrainer_1() {
        return trainer_1;
    }

    public void setTrainer_1(Ent_Trainer trainer_1) {
        this.trainer_1 = trainer_1;
    }

    public Ent_Trainer getTrainer_2() {
        return trainer_2;
    }

    public void setTrainer_2(Ent_Trainer trainer_2) {
        this.trainer_2 = trainer_2;
    }

    public Ent_Trainer getC_winner() {
        return c_winner;
    }

    public void setC_winner(Ent_Trainer c_winner) {
        this.c_winner = c_winner;
    }
}
