package DBconnection;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.*;

import exceptions.InvalidTextFileContentException;

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
    public Connection getConnection() throws SQLException, ClassNotFoundException, IOException, InvalidTextFileContentException {
    	if(conn == null || conn.isClosed()) {
    		establishConnection();
    	}
    	return conn;
    }

    //metodo privato per stabilire la connessione
    private void establishConnection() throws ClassNotFoundException, SQLException, IOException, InvalidTextFileContentException {
        BufferedReader leggiCredenziali = new BufferedReader(new FileReader("src/DBconnection/credentials.txt"));
        String DB_URL = leggiCredenziali.readLine();
        String DB_USERNAME = leggiCredenziali.readLine();
        String DB_PASSWORD = leggiCredenziali.readLine();
        leggiCredenziali.close();
        if(DB_URL==null || DB_USERNAME==null || DB_PASSWORD==null) {
        	throw new InvalidTextFileContentException("File credentials.txt contiene campi vuoti per le credenziali di accesso al database.", null);
        }
        
        Class.forName("oracle.jdbc.driver.OracleDriver");
        OracleDataSource ods = new OracleDataSource();
        ods.setURL(DB_URL);
        ods.setUser(DB_USERNAME);
        ods.setPassword(DB_PASSWORD);
        
        conn = ods.getConnection();
    }

}
