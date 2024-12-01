package org.lightPoke.db.dto;

import java.util.List;

public class TrainerDTO {
    private int id;
    private final String username;
    private final String name;
    private final String nationality;
    private final LicenseDTO license;
    private final List<TournamentDTO> trainerTournamentList;
    private final List<CombatDTO> trainerCombatList;

    public TrainerDTO(final int id, final String username, final String name, final String nationality, LicenseDTO license ,List<TournamentDTO> trainerTournamentList, List<CombatDTO> trainerCombatList) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.nationality = nationality;
        this.license = license;
        this.trainerTournamentList = trainerTournamentList;
        this.trainerCombatList = trainerCombatList;
    }

    public TrainerDTO(final String username, final String name, final String nationality, final LicenseDTO license, List<TournamentDTO> trainerTournamentList, List<CombatDTO> trainerCombatList) {
        this.username = username;
        this.name = name;
        this.license = license;
        this.nationality = nationality;
        this.trainerTournamentList = trainerTournamentList;
        this.trainerCombatList = trainerCombatList;
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

    public List<CombatDTO> getTrainerCombatList() {
        return trainerCombatList;
    }

    @Override
    public String toString() {
        return String.format(
                "TRUser{id=%d, username='%s', nombre='%s', nTorneos='%d', nCombates=%d}",
                id, getUsername(), name, nationality, trainerTournamentList.size(), trainerCombatList.size()
        );
    }
}
