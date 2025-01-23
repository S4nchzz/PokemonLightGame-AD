package org.lightPoke.db.repo;

import org.lightPoke.db.entity.Ent_At_InTournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface Repo_At_InTournament extends JpaRepository<Ent_At_InTournament, Integer> {
}