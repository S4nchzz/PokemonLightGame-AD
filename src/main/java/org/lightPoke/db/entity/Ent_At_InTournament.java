package org.lightPoke.db.entity;

import jakarta.persistence.*;

@Entity(name = "pkm_admin_in_tournament")
@Table(name = "pkm_Admni_in_tournament")
public class Ent_At_InTournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "admin")
    private final String admin;

    @OneToOne
    @JoinColumn(name = "tournament_id")
    private final Ent_Tournament entTournament;

    public Ent_At_InTournament(int id, String admin, Ent_Tournament entTournament) {
        this.admin = admin;
        this.entTournament = entTournament;
        this.id = id;
    }

    public Ent_At_InTournament(String admin, Ent_Tournament entTournament) {
        this.admin = admin;
        this.entTournament = entTournament;
    }

    public int getId() {
        return id;
    }

    public String getAdmin() {
        return admin;
    }

    public Ent_Tournament getTournamentDTO() {
        return entTournament;
    }
}
