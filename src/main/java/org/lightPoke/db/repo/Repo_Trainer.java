package org.lightPoke.db.repo;

import jakarta.transaction.Transactional;
import org.lightPoke.db.entity.Ent_Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Repo_Trainer extends JpaRepository<Ent_Trainer, Integer> {
    @Query("select u from pkm_trainer u where u.username = ?1")
    Ent_Trainer findByUsername(String username);

    @Transactional
    @Modifying
    @Query("DELETE FROM pkm_trainer u where u.username = ?1")
    void removeByUsername(String username);
}
