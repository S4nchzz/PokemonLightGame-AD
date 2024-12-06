package org.lightPoke.db.dto;

import java.util.List;

public class TrainerDTO {
    private int id;
    private final String username;
    private final String name;
    private final String nationality;
    private final LicenseDTO license;

    public TrainerDTO(final int id, final String username, final String name, final String nationality, LicenseDTO license) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.nationality = nationality;
        this.license = license;
    }

    public TrainerDTO(final String username, final String name, final String nationality, final LicenseDTO license) {
        this.username = username;
        this.name = name;
        this.license = license;
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

    public LicenseDTO getLicense() {
        return license;
    }

    @Override
    public String toString() {
        return String.format(
                "TRUser{id=%d, username='%s', nombre='%s', nTorneos='%d'}",
                id, getUsername(), name, nationality
        );
    }
}
