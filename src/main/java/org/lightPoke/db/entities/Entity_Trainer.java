package org.lightPoke.db.entities;

public record Entity_Trainer(int id, String username, String name, String nationality, int license) {
    public Entity_Trainer(final String username, final String name, final String nationality, int license) {
        this(-1, username, name, nationality, license);
    }
}
