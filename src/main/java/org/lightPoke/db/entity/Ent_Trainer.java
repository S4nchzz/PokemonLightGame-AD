package org.lightPoke.db.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

@Entity(name = "pkm_trainer")
@Table(name = "pkm_trainer")
public class Ent_Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private final String username;

    @Column(name = "name")
    private final String name;

    @Column(name = "nationality")
    private final String nationality;

    @OneToOne
    @JoinColumn(name = "license_id")
    private final Ent_License entLicense;

    public Ent_Trainer(final int id, final String username, final String name, final String nationality, Ent_License entLicense) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.nationality = nationality;
        this.entLicense = entLicense;
    }

    public Ent_Trainer(final String username, final String name, final String nationality, final Ent_License entLicense) {
        this.username = username;
        this.name = name;
        this.entLicense = entLicense;
        this.nationality = nationality;
    }

    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getNationality() {
        return nationality;
    }

    public Ent_License getLicense() {
        return entLicense;
    }

    @Override
    public String toString() {
        return String.format(
                "TRUser{id=%d, username='%s', nombre='%s', nTorneos='%s'}",
                id, getUsername(), name, nationality
        );
    }
}
