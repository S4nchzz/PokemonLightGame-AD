package org.lightPoke.db.dto;

import java.util.List;

public class TrainerDTO {
    private int id;
    private final String username;
    private final String name;
    private final String nationality;
    private final LicenseDTO license;
    private final List<TournamentDTO> trainerTournamentList; // ! QUITAR PORQUE UN TORNEO TIENE UN COMBATE Y UN COMBATE TIENE UN ENTRENADOR Y UN ENTRENADOR TIENE UN TORNEO ETC....

    public TrainerDTO(final int id, final String username, final String name, final String nationality, LicenseDTO license, List<TournamentDTO> trainerTournamentList) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.nationality = nationality;
        this.license = license;
        this.trainerTournamentList = trainerTournamentList;
    }

    public TrainerDTO(final String username, final String name, final String nationality, final LicenseDTO license, List<TournamentDTO> trainerTournamentList) {
        this.username = username;
        this.name = name;
        this.license = license;
        this.nationality = nationality;
        this.trainerTournamentList = trainerTournamentList;
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

    public List<TournamentDTO> getTrainerTournamentList() {
        return trainerTournamentList;
    }

    @Override
    public String toString() {
        return String.format(
                "TRUser{id=%d, username='%s', nombre='%s', nTorneos='%d'}",
                id, getUsername(), name, nationality, trainerTournamentList.size()
        );
    }
}
