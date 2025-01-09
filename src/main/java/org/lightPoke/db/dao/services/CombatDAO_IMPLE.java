package org.lightPoke.db.dao.services;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.mysql.cj.protocol.x.ReusableOutputStream;
import org.lightPoke.db.ConnectionDB;
import org.lightPoke.db.dao.interfaces.CombatDAO_IFACE;
import org.lightPoke.db.entities.Entity_Combat;
import org.lightPoke.log.LogManagement;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CombatDAO_IMPLE implements CombatDAO_IFACE {
    private static CombatDAO_IMPLE instance;
    private final LogManagement log;
    private DataSource source;

    private CombatDAO_IMPLE() {
        log = LogManagement.getInstance();

        this.source = ConnectionDB.getConnection();
    }

    public static CombatDAO_IMPLE getInstance() {
        if (instance == null) {
            instance = new CombatDAO_IMPLE();
        }

        return instance;
    }

    @Override
    public Entity_Combat getCombatById(final int id) {
        return null;
    }

    @Override
    public void createCombat(final Entity_Combat entity) {

    }

    @Override
    public void deleteCombat(final Entity_Combat entity) {

    }

    @Override
    public List<Entity_Combat> findCombatsByTournamentId(final int t_id) {
        try {
            Connection conn = source.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM COMBAT WHERE TOURNAMENT_ID = ?");
            st.setInt(1, t_id);

            ResultSet rs = st.executeQuery();

            List<Entity_Combat> combats = new ArrayList<>();
            while (rs.next()) {
                int winner = -1;

                int winnerDBValue;
                if ((winnerDBValue = rs.getInt("C_WINNER")) != 0) {
                    winner = winnerDBValue;
                }


                int trainer1 = -1;
                int trainer1DBValue;
                if ((trainer1DBValue = rs.getInt("TRAINER_1")) != 0) {
                    trainer1 = trainer1DBValue;
                }

                int trainer2 = -1;
                int trainer2DBValue;
                if ((trainer2DBValue = rs.getInt("TRAINER_2")) != 0) {
                    trainer2 = trainer2DBValue;
                }
                combats.add(new Entity_Combat(rs.getInt("ID"), rs.getInt("TOURNAMENT_ID"), rs.getString("DATE"), trainer1, trainer2, winner));
            }

            rs.close();
            st.close();
            conn.close();

            return combats;
        } catch (SQLException e) {
            log.writeLog("Unnable to establish a connection with the DataSource on CreateTrainer function");
        }

        return new ArrayList<>();
    }

    @Override
    public List<Entity_Combat> getCombatsFinishedByTrainerId(int trainer_id) {
        try {
            Connection conn = source.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM COMBAT WHERE (TRAINER_1 IS NOT NULL AND TRAINER_2 IS NOT NULL AND C_WINNER IS NOT NULL) AND (TRAINER_1 = ? OR TRAINER_2 = ?)");
            st.setInt(1, trainer_id);
            st.setInt(2, trainer_id);

            ResultSet rs = st.executeQuery();

            List<Entity_Combat> combats = new ArrayList<>();
            while (rs.next()) {
                combats.add(new Entity_Combat(rs.getInt("ID"), rs.getInt("TOURNAMENT_ID"), rs.getString("DATE"), rs.getInt("TRAINER_1"), rs.getInt("TRAINER_2"), rs.getInt("C_WINNER")));
            }

            rs.close();
            st.close();
            conn.close();

            return combats;
        } catch (SQLException e) {
            log.writeLog("Unnable to establish a connection with the DataSource on CreateTrainer function");
        }

        return null;
    }

    @Override
    public void addCombatsToTournament(int tournamentId) {
        try {
            Connection conn = source.getConnection();
            PreparedStatement st = conn.prepareStatement("INSERT INTO COMBAT (TOURNAMENT_ID) VALUES(?)");
            st.setInt(1, tournamentId);

            st.executeUpdate();

            st.close();
            conn.close();
        } catch (SQLException e) {
            log.writeLog("Unnable to establish a connection with the DataSource on addCmobatsToTournaments() function");
        }
    }

    @Override
    public boolean isTrainerInAnyCombat(int trainerId) {
        try {
            Connection conn = source.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM COMBAT WHERE TRAINER_1 = ? OR TRAINER_2 = ?");
            st.setInt(1, trainerId);
            st.setInt(2, trainerId);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return true;
            }

            st.close();
            conn.close();
        } catch (SQLException e) {
            log.writeLog("Unnable to establish a connection with the DataSource on addCmobatsToTournaments() function");
        }

        return false;
    }

    @Override
    public void addTrainerToTournamentCombat(final int trainerId, final int tournamentId) {
        try {
            Connection conn = source.getConnection();
            List<Entity_Combat> combats = findCombatsByTournamentId(tournamentId);

            int countIdsIn = 0;
            for (Entity_Combat e : combats) {
                if (e.trainer_1() != -1) {
                    countIdsIn++;
                }

                if (e.trainer_2() != -1) {
                    countIdsIn++;
                }
            }

            PreparedStatement update = null;
            switch (countIdsIn) {
                case 0:
                    update = conn.prepareStatement("UPDATE COMBAT " +
                                                        "SET TRAINER_1 = ? " +
                                                        "WHERE ID = ? OR ID = ?");
                    update.setInt(1, trainerId);
                    update.setInt(2, combats.getFirst().id());
                    update.setInt(3, combats.get(1).id());
                    break;

                case 2:
                    update = conn.prepareStatement("UPDATE COMBAT " +
                                                        "SET TRAINER_2 = CASE WHEN ID = ? THEN ? ELSE TRAINER_2 END, " +
                                                        "TRAINER_1 = CASE WHEN ID = ? THEN ? ELSE TRAINER_1 END");
                    update.setInt(1, combats.getFirst().id());
                    update.setInt(2, trainerId);
                    update.setInt(3, combats.get(2).id());
                    update.setInt(4, trainerId);
                    break;

                case 4:
                    update = conn.prepareStatement("UPDATE COMBAT " +
                            "SET TRAINER_2 = CASE WHEN ID = ? THEN ? ELSE TRAINER_2 END, " +
                            "TRAINER_2 = CASE WHEN ID = ? THEN ? ELSE TRAINER_2 END");
                    update.setInt(1, combats.get(1).id());
                    update.setInt(2, trainerId);
                    update.setInt(3, combats.get(2).id());
                    update.setInt(4, trainerId);
                    break;
            }

            if (update != null) {
                update.executeUpdate();
                update.close();
            }

            conn.close();

        } catch (SQLException e) {
            log.writeLog("Unable to establish a connection with the DataSource on addTrainerToTournamentCombat() function");
        }
    }

    @Override
    public List<Entity_Combat> getCombatByWinnerId(int trainerId) {
        try {
            Connection conn = source.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM COMBAT WHERE C_WINNER = ?");
            st.setInt(1, trainerId);

            ResultSet rs = st.executeQuery();

            List<Entity_Combat> entities = new ArrayList<>();
            while (rs.next()) {
                entities.add(new Entity_Combat(rs.getInt("ID"), rs.getInt("TOURNAMENT_ID"), rs.getString("DATE"), rs.getInt("TRAINER_1"), rs.getInt("TRAINER_2"), rs.getInt("C_WINNER")));
            }

            rs.close();
            st.close();
            conn.close();

            return entities;
        } catch (SQLException e) {
            log.writeLog("Unable to establish a connection with the DataSource on getCombatsByWinnerId() function");
        }

        return null;
    }
}
