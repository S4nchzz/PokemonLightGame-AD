package org.lightPoke.db.dao.services;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.lightPoke.db.dao.interfaces.TournamentDAO_IFACE;
import org.lightPoke.db.dto.TournamentDTO;
import org.lightPoke.db.entities.Entity_Tournament;
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

public class TournamentDAO_IMPLE implements TournamentDAO_IFACE {
    private static TournamentDAO_IMPLE instance;
    private DataSource source = null;
    private final LogManagement log;

    private TournamentDAO_IMPLE () {
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

    public static TournamentDAO_IMPLE getInstance() {
        if (instance == null) {
            instance = new TournamentDAO_IMPLE();
        }

        return instance;
    }

    public Entity_Tournament dtoToEntity(final TournamentDTO tournamentDTO) {
        return new Entity_Tournament(tournamentDTO.getName(), tournamentDTO.getRegion(), tournamentDTO.getVictoryPoints());
    }

    @Override
    public void createTournament(Entity_Tournament entity) {
        try {
            Connection conn = source.getConnection();
            PreparedStatement st = conn.prepareStatement("INSERT INTO TOURNAMENT (NAME, COD_REGION, VICTORY_POINTS) VALUES (?, ?, ?)");
            st.setString(1, entity.name());
            st.setString(2, String.valueOf(entity.cod_region()));
            st.setFloat(3, entity.victory_points());

            st.executeUpdate();

            log.writeLog("Tournament " + entity.name() + " created");

            st.close();
            conn.close();
        } catch (SQLException e) {
            log.writeLog("Unnable to establish a connection with the DataSource on createTournament() function");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeTournament(int tournamentId) {

    }

    @Override
    public Entity_Tournament getTournamentById(final int id) {
        try {
            Connection conn = source.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM TOURNAMENT WHERE TOURNAMENT = ?");
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            Entity_Tournament entity = null;

            while (rs.next()) {
                entity = new Entity_Tournament(rs.getInt("ID"), rs.getString("NAME"), rs.getString("COD_REGION").charAt(0), rs.getFloat("VICTORY_POINTS"), rs.getInt("T_WINNER"));
            }

            log.writeLog("Tournament " + entity.id() + " founded");

            rs.close();
            st.close();
            conn.close();

            return entity;
        } catch (SQLException e) {
            log.writeLog("Unnable to establish a connection with the DataSource on createTournament() function");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Entity_Tournament> getTournamentsFromUserById(final int id) {
        try {
            Connection conn = source.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT ID_TOURNAMENT FROM TRAINER_ON_TOURNAMENT WHERE ID_TRAINER = ?");
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            List<Entity_Tournament> tournaments = new ArrayList<>();
            while (rs.next()) {
                PreparedStatement tournamentQuery = conn.prepareStatement("SELECT * FROM TOURNAMENT WHERE ID = ?");
                tournamentQuery.setInt(1, rs.getInt("ID_TOURNAMENT"));

                ResultSet rsTournaments = tournamentQuery.executeQuery();

                while (rsTournaments.next()) {
                    tournaments.add(new Entity_Tournament(rsTournaments.getInt("ID"), rsTournaments.getString("NAME"), rsTournaments.getString("COD_REGION").charAt(0), rsTournaments.getFloat("VICTORY_POINTS"), rsTournaments.getInt("T_WINNER")));
                }

                rsTournaments.close();
            }


            rs.close();
            st.close();
            conn.close();

            return tournaments;
        } catch (SQLException e) {
            log.writeLog("Unnable to establish a connection with the DataSource on CreateTrainer function");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Entity_Tournament> getAllTournaments() {
        try {
            Connection conn = source.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM TOURNAMENT");

            ResultSet rs = st.executeQuery();
            List<Entity_Tournament> tournaments = new ArrayList<>();

            while (rs.next()) {
                tournaments.add(new Entity_Tournament(rs.getInt("ID"), rs.getString("NAME"), rs.getString("COD_REGION").charAt(0), rs.getFloat("VICTORY_POINTS"), rs.getInt("T_WINNER")));
            }

            rs.close();
            st.close();
            conn.close();

            return tournaments;
        } catch (SQLException e) {
            log.writeLog("Unnable to establish a connection with the DataSource on createTournament() function");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Entity_Tournament getTournamentByNameAndRegion(final Entity_Tournament entity) {
        Entity_Tournament newEntity = null;
        try {
            Connection conn = source.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM TOURNAMENT WHERE TOURNAMENT.NAME = ? AND TOURNAMENT.COD_REGION = ?");
            st.setString(1, entity.name());
            st.setString(2, String.valueOf(entity.cod_region()));

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                newEntity = new Entity_Tournament(rs.getInt("ID"), rs.getString("NAME"), rs.getString("COD_REGION").charAt(0), rs.getFloat("VICTORY_POINTS"), rs.getInt("T_WINNER"));
            }

            rs.close();
            st.close();
            conn.close();

            return newEntity;
        } catch (SQLException e) {
            log.writeLog("Unnable to establish a connection with the DataSource on createTournament() function");
            throw new RuntimeException(e);
        }
    }
}
