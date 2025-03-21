package org.lightPoke.db.mysql.entity;

import jakarta.persistence.*;

@Entity(name = "pkm_join_tournament_req")
@Table(name = "pkm_join_tournament_req")
public class Ent_JoinTournamentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Ent_Trainer entTrainer;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Ent_Tournament entTournament;

    public Ent_JoinTournamentRequest(Ent_Trainer entTrainer, Ent_Tournament entTournament) {
        this.entTrainer = entTrainer;
        this.entTournament = entTournament;
    }

    public Ent_JoinTournamentRequest() {}

    public int getId() {
        return id;
    }

    public Ent_Trainer getTrainer() {
        return entTrainer;
    }

    public Ent_Tournament getTournament() {
        return entTournament;
    }
}
