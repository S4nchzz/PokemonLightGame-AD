package org.lightPoke.db.dto;

import java.util.List;

public class TrainerDTO {
    private final int id;
    private final String name;
    private final String nationality;
    private final List<TournamentDTO> trainerTournamentList;
    private final List<CombatDTO> trainerCombatList;

    public TrainerDTO(final int id, final String name, final String nationality, List<TournamentDTO> trainerTournamentList, List<CombatDTO> trainerCombatList) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.trainerTournamentList = trainerTournamentList;
        this.trainerCombatList = trainerCombatList;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public String getNationality() {
        return nationality;
    }

    public List<TournamentDTO> getTrainerTournamentList() {
        return trainerTournamentList;
    }

    public List<CombatDTO> getTrainerCombatList() {
        return trainerCombatList;
    }
}
