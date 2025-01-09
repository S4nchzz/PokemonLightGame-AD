package org.lightPoke.db;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.lightPoke.log.LogManagement;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConnectionDB {
    private static LogManagement log;
    public static DataSource getConnection() {
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

            return source;

        } catch (IOException e) {
            log.writeLog("Unable to find file DB_PROPS.txt on the specified path");
        }

        return null;
    }
}
