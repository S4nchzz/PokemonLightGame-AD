package org.lightPoke.db.entity;

import jakarta.persistence.*;

@Entity(name = "pkm_license")
@Table public class Ent_License {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "expedition_Date")
    private String expedition_Date;

    @Column(name = "points")
    private float points;

    @Column(name = "nVictories")
    private int nVictories;

    public Ent_License () {}

    public Ent_License(String expeditionDate, float points, int nVictories) {
        expedition_Date = expeditionDate;
        this.points = points;
        this.nVictories = nVictories;
    }

    public int getId() {
        return id;
    }

    public String getExpedition_Date() {
        return expedition_Date;
    }

    public float getPoints() {
        return points;
    }

    public void setPoints(final float points) { this.points = points; }

    public int getnVictories() {
        return nVictories;
    }
}
