package org.lightPoke.db.dto;

import java.util.List;

public class TournamentDTO {
    private int id;
    private final String name;
    private final char region;
    private float victoryPoints;
    private int t_winner;
    private List<CombatDTO> combats;

    public TournamentDTO(String name, char region, float victoryPoints) {
        this.name = name;
        this.region = region;
        this.victoryPoints = victoryPoints;
    }

    public TournamentDTO(int id, String name, char region, float victoryPoints, int t_winner, List<CombatDTO> combats) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.victoryPoints = victoryPoints;
        this.t_winner = t_winner;
        this.combats = combats;
    }

    public TournamentDTO(String name, char region) {
        this.name = name;
        this.region = region;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public char getRegion() {
        return region;
    }

    public float getVictoryPoints() {
        return victoryPoints;
    }

    public int getTWinner() {
        return this.t_winner;
    }

    public List<CombatDTO> getCombats() {
        return combats;
    }
}