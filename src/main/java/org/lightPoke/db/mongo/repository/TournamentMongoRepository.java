package org.lightPoke.db.mongo.repository;

import org.lightPoke.db.mongo.collections.TournamentCollection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentMongoRepository extends MongoRepository<TournamentCollection, Integer> {
}