package org.lightPoke.db.entities;

public record Entity_Tournament(int id, String name, char cod_region, float victory_points, int t_winner) {
    public Entity_Tournament(final String name, final char cod_region, final float victory_points) {
        this(-1, name, cod_region, victory_points, -1);
    }
}
