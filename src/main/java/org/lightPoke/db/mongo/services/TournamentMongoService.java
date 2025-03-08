package org.lightPoke.db.mongo.services;

import org.lightPoke.db.mongo.repository.TournamentMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TournamentMongoService {
    @Autowired
    private TournamentMongoRepository tournamentMongoRepository;
}
