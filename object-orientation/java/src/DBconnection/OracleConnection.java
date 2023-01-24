package DBconnection;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.*;
import java.io.*;

public class OracleConnection {
    //Attributi
    private static OracleConnection oraConn = null;
    private Connection conn = null;

    //Costruttori
    private OracleConnection() {}

    //Metodi
    //metodo per ottenere una istanza della classe
    public static OracleConnection getOracleConnection() {
        if(oraConn==null) {
            oraConn = new OracleConnection();
        }
        return oraConn;
    }

    //metodo pubblico per ottenere la connessione
    public Connection getConnection() {
        String DB_USERNAME;
        String DB_PASSWORD;
        String DB_URL;
        BufferedReader leggiCredenziali = null;
        try {
            if (conn == null || conn.isClosed()) {
                leggiCredenziali = new BufferedReader(new FileReader("./object-orientation/java/src/DBconnection/credentials.txt"));
                DB_URL = leggiCredenziali.readLine();
                DB_USERNAME = leggiCredenziali.readLine();
                DB_PASSWORD = leggiCredenziali.readLine();
                Class.forName("oracle.jdbc.driver.OracleDriver");
                OracleDataSource ods = new OracleDataSource();
                ods.setURL(DB_URL);
                ods.setUser(DB_USERNAME);
                ods.setPassword(DB_PASSWORD);
                conn = ods.getConnection();
            }

        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

    return conn;
    }

}
