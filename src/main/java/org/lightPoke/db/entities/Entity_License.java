package org.lightPoke.db.entities;

public record Entity_License(int id, String expedition_date, float points, int n_victories) {
    public Entity_License(final String expedition_date, final float points, final int n_victories) {
        this(-1, expedition_date, points, n_victories);
    }
}
