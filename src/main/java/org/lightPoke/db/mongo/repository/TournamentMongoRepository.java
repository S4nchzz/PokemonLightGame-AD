package org.lightPoke.db.mongo.repository;

import org.lightPoke.db.mongo.collections.TournamentCollection;
import org.lightPoke.db.mongo.dto.TopWinner;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentMongoRepository extends MongoRepository<TournamentCollection, Integer> {
    @Query("{ $and: [ { 'name': ?0 }, { 'region': ?1 } ] }")
    TournamentCollection findByNameAndRegion(String tournamentName, String tournamentRegion);

    @Query("{ region: ?0 }")
    List<TournamentCollection> findByRegion(String s);
}