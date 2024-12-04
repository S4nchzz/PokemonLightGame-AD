package org.lightPoke.db.dto;

public class At_InTournamentDTO {
    private int id;
    private final String admin;
    private final TournamentDTO tournamentDTO;

    public At_InTournamentDTO(int id, String admin, TournamentDTO tournamentDTO) {
        this.admin = admin;
        this.tournamentDTO = tournamentDTO;
        this.id = id;
    }

    public At_InTournamentDTO(String admin, TournamentDTO tournamentDTO) {
        this.admin = admin;
        this.tournamentDTO = tournamentDTO;
    }

    public int getId() {
        return id;
    }

    public String getAdmin() {
        return admin;
    }

    public TournamentDTO getTournamentDTO() {
        return tournamentDTO;
    }
}
