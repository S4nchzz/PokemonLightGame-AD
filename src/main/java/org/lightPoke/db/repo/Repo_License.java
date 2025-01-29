package org.lightPoke.db.repo;

import org.lightPoke.db.entity.Ent_License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface Repo_License extends JpaRepository<Ent_License, Integer> {
}
