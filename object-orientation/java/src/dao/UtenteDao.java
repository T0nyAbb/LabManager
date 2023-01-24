package dao;

import DBconnection.OracleConnection;
import dto.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UtenteDao implements Dao<Utente> {
    Connection conn = null;
    public UtenteDao() {
        this.conn = OracleConnection.getOracleConnection().getConnection();
    }

    @Override
    public List<Utente> getAll() {
        List<Utente> utente = new ArrayList<>();
        try{
            String query = "SELECT * FROM UTENTE";
            PreparedStatement sql = conn.prepareStatement(query);
            ResultSet rs = sql.executeQuery();

            while(rs.next()) {
            Utente user = new Utente(rs.getString("EMAIL"), rs.getString("USERNAME"), rs.getString("USR_PASSWORD"));
            utente.add(user);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
        return utente;
    }


    @Override
    public void insert(Utente utente) {

        try {
            String query = "INSERT INTO UTENTE VALUES(?,(SELECT UTL_I18N.STRING_TO_RAW(LTRIM(RTRIM(?)), 'AL32UTF8') FROM DUAL),?)";
            PreparedStatement sql = conn.prepareStatement(query);
            sql.setString(1, utente.getEmail());
            sql.setString(2, utente.getUsername());
            sql.setString(3, utente.getPassword());

            sql.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void update(Utente utente, List<String> params) {
        //Utente utente = (Utente)o;
        try{
            if(params.size()!=3) throw new RuntimeException("numero di parametri non validi");

            String query = "UPDATE UTENTE SET EMAIL=? USERNAME=? USR_PASSWORD=(SELECT UTL_I18N.STRING_TO_RAW(LTRIM(RTRIM(?)), 'AL32UTF8') FROM DUAL) WHERE USERNAME = ?";
            PreparedStatement sql = conn.prepareStatement(query);
            sql.setString(1, params.get(1));
            sql.setString(2, params.get(2));
            sql.setString(3, params.get(3));
            sql.setString(4, utente.getUsername());
            sql.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Utente utente) {

        try {
            String query = "DELETE FROM UTENTE WHERE USERNAME=? AND EMAIL=?";
            PreparedStatement sql = conn.prepareStatement(query);
            sql.setString(1, utente.getUsername());
            sql.setString(2, utente.getEmail());
            sql.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}

