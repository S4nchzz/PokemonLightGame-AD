package org.lightPoke.db.dao.services;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.lightPoke.db.dao.interfaces.TrainerOnTournamentDAO_IFACE;
import org.lightPoke.db.dto.TournamentDTO;
import org.lightPoke.db.dto.TrainerDTO;
import org.lightPoke.db.entities.Entity_TrainerOnTournament;
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

public class TrainerOnTourmanetDAO_IMPLE implements TrainerOnTournamentDAO_IFACE {
    private static TrainerOnTourmanetDAO_IMPLE instance;
    private final LogManagement log;
    private DataSource source;

    private TrainerOnTourmanetDAO_IMPLE() {
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

    public static TrainerOnTourmanetDAO_IMPLE getInstance() {
        if (instance == null) {
            instance = new TrainerOnTourmanetDAO_IMPLE();
        }

        return instance;
    }

    @Override
    public void addTrainerToTournament(TrainerDTO trainerDTO, TournamentDTO tournamentDTO) {
        try {
            Connection conn = source.getConnection();
            PreparedStatement st = conn.prepareStatement("INSERT INTO TRAINER_ON_TOURNAMENT (ID_TOURNAMENT, ID_TRAINER) VALUES(?, ?)");
            st.setInt(1, tournamentDTO.getId());
            st.setInt(2, trainerDTO.getId());

            st.executeUpdate();

            log.writeLog("User " + trainerDTO.getName() + " succesfully added to tournament " + tournamentDTO.getName());

            st.close();
            conn.close();
        } catch (SQLException e) {
            log.writeLog("Unnable to establish a connection with the DataSource on CreateTrainer function");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Entity_TrainerOnTournament> getTrainersByTournamentId(final int t_id) {
        try {
            Connection conn = source.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM TRAINER_ON_TOURNAMENT WHERE ID_TOURNAMENT = ?");
            st.setInt(1, t_id);

            ResultSet rs = st.executeQuery();

            List<Entity_TrainerOnTournament> entityTrainerOnTournaments = new ArrayList<>();
            while (rs.next()) {
                entityTrainerOnTournaments.add(new Entity_TrainerOnTournament(rs.getInt("ID"), rs.getInt("ID_TOURNAMENT"), rs.getInt("ID_TRAINER")));
            }

            rs.close();
            st.close();
            conn.close();

            return entityTrainerOnTournaments;
        } catch (SQLException e) {
            log.writeLog("Unnable to establish a connection with the DataSource on CreateTrainer function");
            System.out.println(e.getMessage());
        }

        return null;
    }
}
