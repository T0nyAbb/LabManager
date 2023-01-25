package DBconnection;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.*;
import java.io.*;

public class OracleConnection {
   private String DB_USERNAME;
   private String DB_PASSWORD;
   private String DB_URL;
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

        BufferedReader leggiCredenziali = null;
        try {
            if (conn == null || conn.isClosed()) {
                leggiCredenziali = new BufferedReader(new FileReader("./object-orientation/java/src/DBconnection/credentials.txt"));
                this.DB_URL = leggiCredenziali.readLine();
                this.DB_USERNAME = leggiCredenziali.readLine();
                this.DB_PASSWORD = leggiCredenziali.readLine();
                Class.forName("oracle.jdbc.driver.OracleDriver");
                OracleDataSource ods = new OracleDataSource();
                ods.setURL(this.DB_URL);
                ods.setUser(this.DB_USERNAME);
                ods.setPassword(this.DB_PASSWORD);
                conn = ods.getConnection();
            }

        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

    return conn;
    }

}
