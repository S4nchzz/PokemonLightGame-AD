package org.lightPoke.db.mongo.services;

import org.lightPoke.db.mongo.collections.TournamentCollection;
import org.lightPoke.db.mongo.collections.TrainerCollection;
import org.lightPoke.db.mongo.collections.models.CombatModel;
import org.lightPoke.db.mongo.mapper.TrainerMapper;
import org.lightPoke.db.mongo.repository.TrainerMongoRepository;
import org.lightPoke.db.mysql.entity.Ent_Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainerMongoService {
    @Autowired
    private TrainerMongoRepository trainerMongoRepository;

    @Autowired
    private TournamentMongoService tournamentMongoService;

    public void addTrainer(Ent_Trainer trainer) {
        trainerMongoRepository.save(TrainerMapper.mapEntityToCollection(trainer));
    }

    public List<TrainerCollection> findTopWinners() {
        return trainerMongoRepository.findTopWinners();
    }

    public List<TrainerCollection> findAllTrainers() {
        return trainerMongoRepository.findAll();
    }

    public TrainerCollection findSpecificTrainer(String userToFind) {
        return trainerMongoRepository.findSpecificTrainer(userToFind);
    }

    public void updateTrainerWins(Ent_Trainer trainerWinner) {
        trainerMongoRepository.save(TrainerMapper.mapEntityToCollection(trainerWinner));
    }

    public void updateTrainerPoints(Ent_Trainer trainer) {
        trainerMongoRepository.save(TrainerMapper.mapEntityToCollection(trainer));
    }

    public List<TrainerCollection> findTrainersFromTournament(int id) {
        TournamentCollection t = tournamentMongoService.trainersFromTournament(id);
        List<TrainerCollection> trainersMappedToCollection = new ArrayList<>();

        trainersMappedToCollection.add(TrainerMapper.mapModelToCollection(t.getCombats().getFirst().getTrainer_1()));
        trainersMappedToCollection.add(TrainerMapper.mapModelToCollection(t.getCombats().getFirst().getTrainer_2()));
        trainersMappedToCollection.add(TrainerMapper.mapModelToCollection(t.getCombats().get(1).getTrainer_2()));

        return trainersMappedToCollection;
    }

    public void updateTrainer(Ent_Trainer t) {
        trainerMongoRepository.save(TrainerMapper.mapEntityToCollection(t));
    }

    public void updateTrainer(TrainerCollection t) {
        trainerMongoRepository.save(t);
    }
}