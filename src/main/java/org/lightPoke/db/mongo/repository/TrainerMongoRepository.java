package org.lightPoke.db.mongo.repository;

import org.lightPoke.db.mongo.collections.TrainerCollection;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerMongoRepository extends MongoRepository<TrainerCollection, Integer> {
    @Aggregation(pipeline = {
            "{ $sort: { 'license.nVictories': -1 } }",
            "{ $limit: 2 }"
    })
    List<TrainerCollection> findTopWinners();

    @Query("{ username: ?0 }")
    TrainerCollection findSpecificTrainer(String userToFind);
}
