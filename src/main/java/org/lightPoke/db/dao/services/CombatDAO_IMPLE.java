package org.lightPoke.db.dao.services;

import com.mysql.cj.jdbc.MysqlDataSource;
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
        Properties props = new Properties();
        FileInputStream fis = null;
        MysqlDataSource source = null;
        log = LogManagement.getInstance();

        try {
            fis = new FileInputStream("src/main/resources/db/DB_PROPS.txt");
            props.load(fis);
            source = new MysqlDataSource();
            source.setURL(props.getProperty("MYSQL_DB_URL"));
            source.setUser(props.getProperty("MYSQL_DB_USERNAME"));
            source.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));

            this.source = source;
        } catch (IOException e) {
            log.writeLog("Unnable to find file DB_PROPS.txt on the specified path");
        }
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
            PreparedStatement st = conn.prepareStatement("SELECT * FROM COMBAT WHERE ID_TOURNAMENT = ?");
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
                combats.add(new Entity_Combat(rs.getInt("ID"), rs.getInt("ID_TOURNAMENT"), rs.getString("DATE"), trainer1, trainer2, winner));
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
    public List<Entity_Combat> findCombatsByTrainerId(int trainer_id) {
        try {
            Connection conn = source.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM COMBAT WHERE TRAINER_1 = ? OR TRAINER_2 = ?");
            st.setInt(1, trainer_id);
            st.setInt(2, trainer_id);

            ResultSet rs = st.executeQuery();

            List<Entity_Combat> combats = new ArrayList<>();
            while (rs.next()) {
                combats.add(new Entity_Combat(rs.getInt("ID"), rs.getInt("ID_TOURNAMENT"), rs.getString("DATE"), rs.getInt("TRAINER_1"), rs.getInt("TRAINER_2"), rs.getInt("C_WINNER")));
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
            PreparedStatement st = conn.prepareStatement("INSERT INTO COMBAT (ID_TOURNAMENT) VALUES(?)");
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
            PreparedStatement countTrainersSt = conn.prepareStatement("SELECT COUNT(*) FROM COMBAT WHERE ID_TOURNAMENT = ? AND (TRAINER_1 = ? OR TRAINER_2 = ?)");
            countTrainersSt.setInt(1, tournamentId);
            countTrainersSt.setInt(2, trainerId);
            countTrainersSt.setInt(3, trainerId);

            ResultSet countrs = countTrainersSt.executeQuery();

            int countTrainersInCombats = 0;
            if (countrs.next()) {
                countTrainersInCombats = countrs.getInt(1);
            }

            if (countTrainersInCombats < 2) {
                PreparedStatement searchCombatToAddTrainer = conn.prepareStatement("SELECT * FROM COMBAT WHERE ID_TOURNAMENT = ? AND (TRAINER_1 != ? OR TRAINER_2 != ?) AND (TRAINER_1 IS NULL OR TRAINER_2 IS NULL)");
                searchCombatToAddTrainer.setInt(1, tournamentId);
                searchCombatToAddTrainer.setInt(2, trainerId);
                searchCombatToAddTrainer.setInt(3, trainerId);

                ResultSet combatSearch = searchCombatToAddTrainer.executeQuery();

                while (combatSearch.next()) {
                    String date = "";

                    String dateDBValue;
                    if ((dateDBValue = combatSearch.getString("DATE")) != "") {
                        date = dateDBValue;
                    }

                    int t1 = -1;

                    int t1DBValue;
                    if ((t1DBValue = combatSearch.getInt("TRAINER_1")) != 0) {
                        t1 = t1DBValue;
                    }

                    int t2 = -1;

                    int t2DBValue;
                    if ((t2DBValue = combatSearch.getInt("TRAINER_2")) != 0) {
                        t2 = t2DBValue;
                    }

                    int tw = -1;

                    int twDBValue;
                    if ((twDBValue = combatSearch.getInt("C_WINNER")) == 0) {
                        tw = twDBValue;
                    }


                    Entity_Combat combat = new Entity_Combat(combatSearch.getInt("ID"), combatSearch.getInt("ID_TOURNAMENT"), combatSearch.getString("DATE"), t1, t2, tw);

                    if (combat.trainer_1() != trainerId && combat.trainer_2() != trainerId) {
                        String whereToPlaceValue = "";
                        if (combat.trainer_1() == -1) {
                            whereToPlaceValue = "TRAINER_1";
                        } else if (combat.trainer_2() == -1) {
                            whereToPlaceValue = "TRAINER_2";
                        }

                        PreparedStatement update = conn.prepareStatement("UPDATE COMBATS SET ? WHERE ID = ?");
                        update.setString(1, whereToPlaceValue);
                        update.setInt(2, combat.id());


                        combatSearch.close();
                        update.close();
                        countrs.close();
                        countTrainersSt.close();
                        conn.close();

                        return;
                    }
                }
            }

            countrs.close();
            countTrainersSt.close();
            conn.close();
        } catch (SQLException e) {
            log.writeLog("Unnable to establish a connection with the DataSource on addCmobatsToTournaments() function");
        }
    }
}
