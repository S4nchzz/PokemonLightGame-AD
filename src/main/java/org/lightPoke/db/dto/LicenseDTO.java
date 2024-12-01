package org.lightPoke.db.dto;

public class LicenseDTO {
    private int id;
    private final String expedition_Date;
    private final float points;
    private final int nVictories;

    public LicenseDTO(int id, String expeditionDate, float points, int nVictories) {
        this.id = id;
        expedition_Date = expeditionDate;
        this.points = points;
        this.nVictories = nVictories;
    }

    public LicenseDTO (String expeditionDate, float points, int nVictories) {
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

    public int getnVictories() {
        return nVictories;
    }
}
