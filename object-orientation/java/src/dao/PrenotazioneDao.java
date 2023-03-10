package dao;

import dto.*;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PrenotazioneDao implements Dao<Prenotazione> {
	private Connection conn;
	
    public PrenotazioneDao(Connection conn) {
        this.conn = conn;
    }
    
    @Override
    public List<Prenotazione> getAll() throws SQLException{
        List<Prenotazione> prenotazione = new ArrayList<>();
        String query = "SELECT * FROM PRENOTAZIONE JOIN UTENTE U on U.USERNAME = PRENOTAZIONE.USERNAME JOIN STRUMENTO S on S.ID_STRUMENTO = PRENOTAZIONE.ID_STRUMENTO";
        PreparedStatement sql = conn.prepareStatement(query);
        ResultSet rs = sql.executeQuery();
        List<Utente> utenti = new UtenteDao(conn).getAll();
        List<Strumento> strumenti = new StrumentoDao(conn).getAll();

        while(rs.next()) {
            for(Utente utente:utenti) {
                for(Strumento strumento:strumenti) {
                    if(rs.getString("USERNAME").compareTo(utente.getUsername())==0 && rs.getInt("ID_STRUMENTO") == strumento.getId()) {
                        Prenotazione p = new Prenotazione(rs.getInt("ID_PRENOTAZIONE"), strumento, utente, rs.getDate("DATAPRENOTAZIONE"), rs.getInt("DURATA"), rs.getDate("DATAINIZIO"));
                        prenotazione.add(p);
                    }
                }
            }

        }
        rs.close();
        sql.close();
        return prenotazione;
    }
    
    public List<Prenotazione> getByUtente(Utente u) throws SQLException{
        List<Prenotazione> prenotazione = new ArrayList<>();
        String query = "SELECT * FROM PRENOTAZIONE JOIN STRUMENTO S on S.ID_STRUMENTO = PRENOTAZIONE.ID_STRUMENTO WHERE PRENOTAZIONE.USERNAME = ?";
        PreparedStatement sql = conn.prepareStatement(query);
        sql.setString(1, u.getUsername());
        ResultSet rs = sql.executeQuery();
        List<Strumento> strumenti = new StrumentoDao(conn).getAll();

        while(rs.next()) {
            for(Strumento strumento:strumenti) {
                if(rs.getInt("ID_STRUMENTO") == strumento.getId()) {
                    Prenotazione p = new Prenotazione(rs.getInt("ID_PRENOTAZIONE"), strumento, u, rs.getDate("DATAPRENOTAZIONE"), rs.getInt("DURATA"), rs.getDate("DATAINIZIO"));
                    prenotazione.add(p);
                }
            }
        }
        rs.close();
        sql.close();
        return prenotazione;
    }

    public int getCountByUtente(Utente u) throws SQLException{
        String query = "SELECT COUNT(PRENOTAZIONE.ID_PRENOTAZIONE) FROM PRENOTAZIONE WHERE PRENOTAZIONE.USERNAME = ?";
        PreparedStatement sql = conn.prepareStatement(query);
        sql.setString(1, u.getUsername());
        ResultSet rs = sql.executeQuery();

        int count = 0;
        if(rs.next()) {
            count = rs.getInt(1);
        }
        rs.close();
        sql.close();
        return count;
    }
    
    @Override
    public void insert(Prenotazione prenotazione) throws SQLException {
        Utente u = prenotazione.getUtente();
        Strumento s = prenotazione.getStrumento();
        String query = "INSERT INTO PRENOTAZIONE(DATAINIZIO, DURATA, USERNAME, ID_STRUMENTO) VALUES(TO_DATE(?, 'YYYY/MM/DD HH24:MI'), ?, ?, ?)";
		DateFormat timeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm"); 
        PreparedStatement sql = conn.prepareStatement(query);
        sql.setString(1, timeFormat.format(prenotazione.getDataInizio()));
        sql.setInt(2, prenotazione.getDurata());
        sql.setString(3, u.getUsername());
        sql.setInt(4, s.getId());
        sql.executeUpdate();
        sql.close();

    }

    @Override
    public void update(Prenotazione prenotazione, List<String> params) throws SQLException {
        if(params.size()!=2) throw new RuntimeException("numero di parametri non validi");

        String query = "UPDATE PRENOTAZIONE SET DATAINIZIO= TO_DATE(?, 'YYYY/MM/DD HH24:MI'), DURATA=? WHERE ID_PRENOTAZIONE = ?";
        PreparedStatement sql = conn.prepareStatement(query);
        sql.setString(1, params.get(0));
        sql.setInt(2, Integer.parseInt(params.get(1)));
        sql.setInt(3, prenotazione.getId());
        sql.executeUpdate();
        sql.close();

    }

    @Override
    public void delete(Prenotazione prenotazione) throws SQLException {
        String query = "DELETE FROM PRENOTAZIONE WHERE ID_PRENOTAZIONE = ?";
        PreparedStatement sql = conn.prepareStatement(query);
        sql.setInt(1, prenotazione.getId());
        sql.executeUpdate();
        sql.close();

    }

    public List<Prenotazione> getCalendarByStrumento(Strumento strumento) throws SQLException {
        String query = "SELECT * FROM PRENOTAZIONE WHERE ID_STRUMENTO = ? ORDER BY DATAINIZIO DESC";
        PreparedStatement sql = conn.prepareStatement(query);
        sql.setInt(1, strumento.getId());
        ResultSet rs = sql.executeQuery();
        List<Prenotazione> prenotazioni = new ArrayList<>();
        List<Utente> utenti = new UtenteDao(conn).getAll();
        List<Strumento> strumenti = new StrumentoDao(conn).getAll();
        while(rs.next()) {
            for(Utente utente:utenti) {
                for(Strumento s:strumenti) {
                    if(rs.getString("USERNAME")!= null && rs.getString("USERNAME").equals(utente.getUsername()) && s.getId()==strumento.getId()) {
                        Prenotazione p = new Prenotazione(rs.getInt("ID_PRENOTAZIONE"), s, utente, rs.getDate("DATAPRENOTAZIONE"), rs.getInt("DURATA"), rs.getDate("DATAINIZIO"));
                        prenotazioni.add(p);
                    }

                }

            }

        }
        return prenotazioni;
    }

}
