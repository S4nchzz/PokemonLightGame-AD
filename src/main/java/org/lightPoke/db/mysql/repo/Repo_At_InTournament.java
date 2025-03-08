package org.lightPoke.db.mysql.repo;

import org.lightPoke.db.mysql.entity.Ent_At_InTournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Repo_At_InTournament extends JpaRepository<Ent_At_InTournament, Integer> {
    @Query("select a from pkm_admin_in_tournament a where a.admin = ?1")
    Ent_At_InTournament findByAdmin(String admin);

    @Query("select a.id from pkm_admin_in_tournament a where a.entTournament.id = ?1")
    List<Integer> getRequestsForTournament(int id);
}