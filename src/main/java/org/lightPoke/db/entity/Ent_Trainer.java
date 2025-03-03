package org.lightPoke.db.entity;

import jakarta.persistence.*;

@Entity(name = "pkm_trainer")
@Table(name = "pkm_trainer")
public class Ent_Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "nationality", nullable = false)
    private String nationality;

    @OneToOne
    @JoinColumn(name = "license_id")
    private Ent_License entLicense;

    public Ent_Trainer(){}

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

    public void setUsername(String username) {
        this.username = username;
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
