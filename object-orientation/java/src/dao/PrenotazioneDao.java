package dao;

import dto.*;
import exceptions.NoDataFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                    if(rs.getString(6).equals(utente.getUsername()) && rs.getString(11).equals(strumento.getDescrizione()) && rs.getString(12).equals(strumento.getSchedaTecnica())) {
                        Prenotazione p = new Prenotazione(strumento, utente, rs.getTimestamp(4), rs.getInt(3), rs.getTimestamp(2));
                        prenotazione.add(p);
                    }
                }
            }

        }
        rs.close();
        sql.close();
        return prenotazione;
    }

    @Override
    public void insert(Prenotazione prenotazione) throws SQLException {
        Utente u = prenotazione.getUtente();
        Strumento s = prenotazione.getStrumento();
        String query = "INSERT INTO PRENOTAZIONE(DATAINIZIO, DURATA, USERNAME, ID_STRUMENTO) VALUES((SELECT TO_DATE(?, 'YYYY/MM/DD HH24:MI') FROM DUAL), ?, ?,(SELECT ID_STRUMENTO FROM STRUMENTO WHERE UPPER(STRUMENTO.DESCRIZIONE)=UPPER(?) AND UPPER(STRUMENTO.SCHEDATECNICA)=UPPER(?) FETCH NEXT 1 ROWS ONLY))";
        PreparedStatement sql = conn.prepareStatement(query);
        sql.setString(1, prenotazione.getDataInizio().toString());
        sql.setInt(2, prenotazione.getDurata());
        sql.setString(3, u.getUsername());
        sql.setString(4, s.getDescrizione());
        sql.setString(5, s.getSchedaTecnica());
        sql.executeUpdate();
        sql.close();

    }

    @Override
    public void update(Prenotazione prenotazione, List<String> params) throws SQLException {
        if(params.size()!=2) throw new RuntimeException("numero di parametri non validi");

        String query = "UPDATE PRENOTAZIONE SET DATAINIZIO=(SELECT TO_DATE(?, 'YYYY/MM/DD HH24:MI') FROM DUAL), DURATA=? WHERE DATAINIZIO=(SELECT TO_DATE(?, 'YYYY/MM/DD HH24:MI') FROM DUAL) AND DURATA=?";
        PreparedStatement sql = conn.prepareStatement(query);
        sql.setString(1, params.get(1));
        sql.setInt(2, Integer.parseInt(params.get(2)));
        sql.setString(3, prenotazione.getDataPrenotazione().toString());
        sql.setInt(4, prenotazione.getDurata());
        sql.executeUpdate();
        sql.close();

    }

    @Override
    public void delete(Prenotazione prenotazione) throws SQLException {
        String query = "DELETE FROM PRENOTAZIONE WHERE DATAINIZIO=(SELECT TO_DATE(?, 'YYYY/MM/DD HH24:MI') FROM DUAL) AND DURATA=?";
        PreparedStatement sql = conn.prepareStatement(query);
        sql.setString(1, prenotazione.getDataInizio().toString());
        sql.setInt(2, prenotazione.getDurata());
        sql.executeUpdate();
        sql.close();

    }
    public int getNumPrenByUsername(String username) throws SQLException, NoDataFoundException {
        int numeroPrenotazioni;
        String query = "SELECT COUNT(*) AS NUM_PREN FROM PRENOTAZIONE P WHERE P.USERNAME=?";
        PreparedStatement sql = conn.prepareStatement(query);
        sql.setString(1, username);
        ResultSet rs = sql.executeQuery();

        if(rs.next()) {
            numeroPrenotazioni= rs.getInt(1);
        } else {
            throw new NoDataFoundException();
        }

        return numeroPrenotazioni;
    }
    public String getUsageByStrumento(Strumento strumento) throws SQLException {
        String utilizzo;
        Postazione post = strumento.getPostazione();
        Sede sede = post.getSede();
        String query = "SELECT SUM(DURATA), PRENOTAZIONE.ID_STRUMENTO, S.DESCRIZIONE,S.SCHEDATECNICA  FROM PRENOTAZIONE JOIN STRUMENTO S on S.ID_STRUMENTO = PRENOTAZIONE.ID_STRUMENTO WHERE S.DESCRIZIONE=? AND S.SCHEDATECNICA=? AND PRENOTAZIONE.DATAINIZIO<(SELECT SYSDATE FROM DUAL) GROUP BY PRENOTAZIONE.ID_STRUMENTO, S.DESCRIZIONE , S.SCHEDATECNICA";
        PreparedStatement sql = conn.prepareStatement(query);
        sql.setString(1, strumento.getDescrizione());
        sql.setString(2, strumento.getSchedaTecnica());
        ResultSet rs = sql.executeQuery();

        if(rs.next()) {
            utilizzo = "Lo strumento " + strumento.getDescrizione() + " è stato utilizzato per " + rs.getInt(1) + " ore.";
        } else {
            utilizzo = "Lo strumento " + strumento.getDescrizione() + " non è mai stato utilizzato.";
        }
        return utilizzo;


    }
}
