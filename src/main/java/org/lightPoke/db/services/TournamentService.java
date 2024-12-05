package org.lightPoke.db.services;

import org.lightPoke.db.dao.services.CombatDAO_IMPLE;
import org.lightPoke.db.dao.services.TournamentDAO_IMPLE;
import org.lightPoke.db.dao.services.TrainerOnTourmanetDAO_IMPLE;
import org.lightPoke.db.dto.At_InTournamentDTO;
import org.lightPoke.db.dto.CombatDTO;
import org.lightPoke.db.dto.TournamentDTO;
import org.lightPoke.db.dto.TrainerDTO;
import org.lightPoke.db.entities.Entity_Combat;
import org.lightPoke.db.entities.Entity_Tournament;
import org.lightPoke.db.entities.Entity_Trainer;
import org.lightPoke.db.entities.Entity_TrainerOnTournament;
import org.lightPoke.users.ATUser;

import java.util.ArrayList;
import java.util.List;

public class TournamentService {
    private static TournamentService instance;
    private final TournamentDAO_IMPLE tournamentDAO;

    private TournamentService() {
        this.tournamentDAO = TournamentDAO_IMPLE.getInstance();
    }

    public static TournamentService getInstance() {
        if (instance == null) {
            instance = new TournamentService();
        }

        return instance;
    }

    private TournamentDTO entityToDto(final Entity_Tournament entityTournament) {
        CombatService combatService = CombatService.getInstance();
        TrainerService trainerService = TrainerService.getInstance();

        List<CombatDTO> combats = combatService.getCombatsByTournamentId(entityTournament.id());

        TrainerDTO winner = null;
        if (entityTournament.t_winner() != -1) {
            winner = trainerService.getTrainerById(entityTournament.t_winner());
        }

        return new TournamentDTO(entityTournament.id(), entityTournament.name(), entityTournament.cod_region(), entityTournament.victory_points(), winner, combats);
    }

    private Entity_Tournament dtoToEntity(final TournamentDTO tournamentDTO) {
        int winnerId = -1;
        if (tournamentDTO.getTWinner() != null) {
            winnerId = tournamentDTO.getTWinner().getId();
        }
        return new Entity_Tournament(tournamentDTO.getId(), tournamentDTO.getName(), tournamentDTO.getRegion(), tournamentDTO.getVictoryPoints(), winnerId);
    }

    public List<TournamentDTO> getAllTournaments() {
        List<Entity_Tournament> entity_tournaments = tournamentDAO.getAllTournaments();

        List<TournamentDTO> tournamentsDto = new ArrayList<>();

        for (Entity_Tournament t : entity_tournaments) {
            tournamentsDto.add(entityToDto(t));
        }

        return tournamentsDto;
    }

    public void addTrainerToTournament(TrainerDTO trainerDTO, TournamentDTO tournamentDTO) {
        TrainerOnTournamentService trainerOnTournamentService = TrainerOnTournamentService.getInstance();
        trainerOnTournamentService.addTrainerToTournament(trainerDTO, tournamentDTO);
    }

    /**
     * Convertimos el tournament DTO que tiene solo el nombre la region y los puntos de victoria
     * en una entidad y la enviamos a crear el torneo, esta creacion nos devolvera una entidad
     * nueva con el id, la reconvertimos a DTO ya que ahora tiene constancia en la base de datos
     * @param tournamentDTO
     * @param atUser
     */
    public void createTournament(final TournamentDTO tournamentDTO, final ATUser atUser) {
        TournamentDTO tournament = entityToDto(tournamentDAO.createTournament(dtoToEntity(tournamentDTO)));

        At_InTournamentService atInTournamentService = At_InTournamentService.getInstance();
        atInTournamentService.addTournamentAdmin(atUser.getUsername(), tournament.getId());
    }

    public boolean isTournamentAvailable(TournamentDTO tournamentDTO) {
        return tournamentDAO.getTournamentByNameAndRegion(dtoToEntity(tournamentDTO)) == null;
    }

    public List<TournamentDTO> getTournamentsByUserId(int id) {
        List<TournamentDTO> tournaments = new ArrayList<>();

        for (Entity_Tournament e : tournamentDAO.getTournamentsFromUserById(id)) {
            tournaments.add(entityToDto(e));
        }

        return tournaments;
    }

    public TournamentDTO getTournamentById(final int t_id) {
        return entityToDto(tournamentDAO.getTournamentById(t_id));
    }
}
