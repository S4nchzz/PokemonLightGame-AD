package org.lightPoke.db.repo;

import org.lightPoke.db.entity.Ent_Combat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Repo_Combat extends JpaRepository<Ent_Combat, Integer> {
    @Query()
    List<Ent_Combat> findByTrainerId(int trainer_id);
}
