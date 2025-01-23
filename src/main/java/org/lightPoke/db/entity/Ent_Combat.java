package org.lightPoke.db.entity;

import jakarta.persistence.*;

@Entity(name = "pkm_combat")
@Table(name = "pkm_combat")
public class Ent_Combat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final int id;

    @Column(name = "date")
    private final String date;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private final Ent_Tournament entTournament;

    @ManyToOne
    @JoinColumn(name = "trainer_1")
    private final Ent_Trainer trainer_1;

    @ManyToOne
    @JoinColumn(name = "trainer_2")
    private final Ent_Trainer trainer_2;

    @ManyToOne
    @JoinColumn(name = "c_winner")
    private final Ent_Trainer c_winner;

    public Ent_Combat(int id, String date, Ent_Tournament entTournament, Ent_Trainer trainer1, Ent_Trainer trainer2, Ent_Trainer cWinner) {
        this.id = id;
        this.date = date;
        this.entTournament = entTournament;
        this.trainer_1 = trainer1;
        this.trainer_2 = trainer2;
        this.c_winner = cWinner;
    }

    public int getId() {
        return this.id;
    }

    public String getDate() {
        return date;
    }

    public Ent_Tournament getTournament() { return this.entTournament; }

    public Ent_Trainer getTrainer_1() {
        return trainer_1;
    }

    public Ent_Trainer getTrainer_2() {
        return trainer_2;
    }

    public Ent_Trainer getC_winner() {
        return c_winner;
    }
}
