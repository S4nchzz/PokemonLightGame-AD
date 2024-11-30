package org.lightPoke.db.entities;

public record Entity_Trainer(int id, String username, String name, String nationality) {
    public Entity_Trainer(final String username, final String name, final String nationality) {
        this(-1, username, name, nationality);
    }
}
