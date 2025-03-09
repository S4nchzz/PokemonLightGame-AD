package org.lightPoke.db.mongo.services;

import org.lightPoke.db.mongo.collections.TournamentCollection;
import org.lightPoke.db.mongo.mapper.TournamentMapper;
import org.lightPoke.db.mongo.repository.TournamentMongoRepository;
import org.lightPoke.db.mysql.entity.Ent_Combat;
import org.lightPoke.db.mysql.entity.Ent_Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournamentMongoService {
    @Autowired
    private TournamentMongoRepository tournamentMongoRepository;

    public void save(Ent_Tournament tournament, List<Ent_Combat> combatList) {
        tournamentMongoRepository.save(TournamentMapper.mapTournamentEntityToCollection(tournament, combatList));
    }

    public TournamentCollection findByNameAndRegion(String tournamentName, char tournamentRegion) {
        return tournamentMongoRepository.findByNameAndRegion(tournamentName, String.valueOf(tournamentRegion));
    }

    public List<TournamentCollection> findAll() {
        return tournamentMongoRepository.findAll();
    }

    public List<TournamentCollection> findByRegion(char tournamentRegion) {
        return tournamentMongoRepository.findByRegion(String.valueOf(tournamentRegion));
    }
}
