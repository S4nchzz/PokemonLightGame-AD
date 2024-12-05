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
                combats.add(new Entity_Combat(rs.getInt("ID"), rs.getInt("ID_TOURNAMENT"), rs.getString("DATE"), rs.getInt("TRAINER_1"), rs.getInt("TRAINER_2"), winner));
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
}
