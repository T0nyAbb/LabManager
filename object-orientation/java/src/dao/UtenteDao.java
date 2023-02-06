package dao;

import dto.Utente;
import exceptions.IncorrectCredentialsException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UtenteDao implements Dao<Utente> {
	private Connection conn;
	
    public UtenteDao(Connection conn) {
        this.conn = conn;
    }

	public Utente getByCredentials(String username, String usr_password) throws SQLException, IncorrectCredentialsException {
        Utente utente = null;
        String query = "SELECT * FROM UTENTE WHERE UPPER(USERNAME) = UPPER(?) AND USR_PASSWORD = ENCRYPT_PWD(?)";
        PreparedStatement sql = conn.prepareStatement(query);
	    sql.setString(1, username);
	    sql.setString(2, usr_password);
	    ResultSet rs = sql.executeQuery();
	    
	    if(rs.next()) {
	        utente = new Utente(rs.getString("EMAIL"), rs.getString("USERNAME"), rs.getString("USR_PASSWORD"));
	    }else {
	        throw new IncorrectCredentialsException();
	    }
	    
        rs.close();
	    sql.close();
        return utente;
    }
    
    @Override
    public List<Utente> getAll() throws SQLException {
        List<Utente> utente = new ArrayList<>();
        String query = "SELECT * FROM UTENTE";
        PreparedStatement sql = conn.prepareStatement(query);
        ResultSet rs = sql.executeQuery();
        
        while(rs.next()) {
            Utente user = new Utente(rs.getString("EMAIL"), rs.getString("USERNAME"), rs.getString("USR_PASSWORD"));
            utente.add(user);
        }
        
        rs.close();
        sql.close();
        return utente;
    }


    @Override
    public void insert(Utente utente) throws SQLException {
        String query = "INSERT INTO UTENTE(USERNAME, USR_PASSWORD, EMAIL) VALUES(?,(SELECT UTL_I18N.STRING_TO_RAW(LTRIM(RTRIM(?)), 'AL32UTF8') FROM DUAL),?)";
        PreparedStatement sql = conn.prepareStatement(query);
        sql.setString(1, utente.getUsername());
        sql.setString(2, utente.getPassword());
        sql.setString(3, utente.getEmail());
        sql.executeUpdate();
        sql.close();
    }
    
    @Override
    public void update(Utente utente, List<String> params) throws SQLException {
        if(params.size()!=3) throw new RuntimeException("numero di parametri non validi");

        String query = "UPDATE UTENTE SET EMAIL=?, USERNAME=?, USR_PASSWORD=(SELECT UTL_I18N.STRING_TO_RAW(LTRIM(RTRIM(?)), 'AL32UTF8') FROM DUAL) WHERE UPPER(USERNAME)=UPPER(?)";
        PreparedStatement sql = conn.prepareStatement(query);
        sql.setString(1, params.get(0));
        sql.setString(2, params.get(1));
        sql.setString(3, params.get(2));
        sql.setString(4, utente.getUsername());
        sql.executeUpdate();
        sql.close();
    }

    @Override
    public void delete(Utente utente) throws SQLException{
        String query = "DELETE FROM UTENTE WHERE USERNAME=? AND EMAIL=?";
        PreparedStatement sql = conn.prepareStatement(query);
        sql.setString(1, utente.getUsername());
        sql.setString(2, utente.getEmail());
        sql.executeUpdate();
        sql.close();
    }
}

