package dao;

import DBconnection.OracleConnection;
import dto.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PrenotazioneDao implements Dao<Prenotazione> {
    Connection conn = null;
    public PrenotazioneDao() {
        this.conn = OracleConnection.getOracleConnection().getConnection();
    }
    @Override
    public List<Prenotazione> getAll() {

        List<Prenotazione> prenotazione = new ArrayList<>();
        try{
            String query = "SELECT * FROM PRENOTAZIONE JOIN UTENTE U on U.USERNAME = PRENOTAZIONE.USERNAME JOIN STRUMENTO S on S.ID_STRUMENTO = PRENOTAZIONE.ID_STRUMENTO";
            PreparedStatement sql = conn.prepareStatement(query);
            ResultSet rs = sql.executeQuery();
            List<Utente> utenti = new UtenteDao().getAll();
            List<Strumento> strumenti = new StrumentoDao().getAll();

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
        } catch(Exception e) {
            e.printStackTrace();
        }
        return prenotazione;
    }

    @Override
    public void insert(Prenotazione prenotazione) {
        try{
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
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Prenotazione prenotazione, List<String> params) {
        try{
            if(params.size()!=2) throw new RuntimeException("numero di parametri non validi");

            String query = "UPDATE PRENOTAZIONE SET DATAINIZIO=(SELECT TO_DATE(?, 'YYYY/MM/DD HH24:MI') FROM DUAL), DURATA=? WHERE DATAINIZIO=(SELECT TO_DATE(?, 'YYYY/MM/DD HH24:MI') FROM DUAL) AND DURATA=?";
            PreparedStatement sql = conn.prepareStatement(query);
            sql.setString(1, params.get(1));
            sql.setInt(2, Integer.parseInt(params.get(2)));
            sql.setString(3, prenotazione.getDataPrenotazione().toString());
            sql.setInt(4, prenotazione.getDurata());
            sql.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Prenotazione prenotazione) {
        try{
            String query = "DELETE FROM PRENOTAZIONE WHERE DATAINIZIO=(SELECT TO_DATE(?, 'YYYY/MM/DD HH24:MI') FROM DUAL) AND DURATA=?";
            PreparedStatement sql = conn.prepareStatement(query);
            sql.setString(1, prenotazione.getDataInizio().toString());
            sql.setInt(2, prenotazione.getDurata());
            sql.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}
