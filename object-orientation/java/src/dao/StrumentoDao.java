package dao;

import DBconnection.OracleConnection;
import dto.Postazione;
import dto.Sede;
import dto.Strumento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StrumentoDao implements Dao<Strumento> {
    Connection conn = null;
    public StrumentoDao() {
        this.conn = OracleConnection.getOracleConnection().getConnection();
    }
    @Override
    public List<Strumento> getAll() {
        List<Strumento> strumento = new ArrayList<>();
        try{
            String query = "SELECT * FROM STRUMENTO JOIN POSTAZIONE P on STRUMENTO.ID_POSTAZIONE = P.ID_POSTAZIONE";
            PreparedStatement sql = conn.prepareStatement(query);
            ResultSet rs = sql.executeQuery();
            List<Postazione> postazioni = new PostazioneDao().getAll();
            while(rs.next()) {
                for(Postazione postazione:postazioni) {
                    if(rs.getString("STRUMENTO.ID_POSTAZIONE").equals(rs.getString("P.ID_POSTAZIONE")) && postazione.getNome().equals(rs.getString("NOME"))) {
                        Strumento s = new Strumento(postazione, rs.getString("DESCRIZIONE"), rs.getString("SCHEDATECNICA"));
                        strumento.add(s);

                    }
                }

            }
            rs.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return strumento;
    }


    @Override
    public void insert(Strumento strumento) {
        try{
            Postazione p = strumento.getPostazione();
            Sede s = p.getSede();
            String query = "INSERT INTO STRUMENTO(DESCRIZIONE, SCHEDATECNICA,  ID_POSTAZIONE) VALUES(?, ?,(SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(POSTAZIONE.NOME)=UPPER(?) AND UPPER(SEDE.INDIRIZZO)=UPPER(?) FETCH NEXT 1 ROWS ONLY))";
            PreparedStatement sql = conn.prepareStatement(query);
            sql.setString(1, strumento.getDescrizione());
            sql.setString(2, strumento.getSchedaTecnica());
            sql.setString(3, p.getNome());
            sql.setString(4, s.getIndirizzo());
            sql.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Strumento strumento, List<String> params) {
        try{
            if(params.size()!=2) throw new RuntimeException("numero di parametri non validi");
            Postazione p = strumento.getPostazione();
            Sede s = p.getSede();
            String query = "UPDATE STRUMENTO SET DESCRIZIONE=?, SCHEDATECNICA=? WHERE UPPER(DESCRIZIONE)=UPPER(?) AND UPPER(SCHEDATECNICA)=UPPER(?) AND ID_STRUMENTO=(SELECT ID_STRUMENTO FROM STRUMENTO S JOIN POSTAZIONE P ON S.ID_POSTAZIONE=P.ID_POSTAZIONE JOIN SEDE ON SEDE.ID_SEDE=P.ID_SEDE WHERE UPPER(P.NOME)=UPPER(?) AND UPPER(SEDE.INDIRIZZO)=UPPER(?))";
            PreparedStatement sql = conn.prepareStatement(query);
            sql.setString(1, params.get(1));
            sql.setString(2, params.get(2));
            sql.setString(3, strumento.getDescrizione());
            sql.setString(4, strumento.getSchedaTecnica());
            sql.setString(5, p.getNome());
            sql.setString(6, s.getIndirizzo());
            sql.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Strumento strumento) {
        try {
            Postazione p = strumento.getPostazione();
            Sede s = p.getSede();
            String query = "DELETE FROM STRUMENTO WHERE UPPER(DESCRIZIONE)=UPPER(?) AND UPPER(SCHEDATECNICA)=UPPER(?) AND ID_STRUMENTO=(SELECT ID_STRUMENTO FROM STRUMENTO S JOIN POSTAZIONE P ON S.ID_POSTAZIONE=P.ID_POSTAZIONE JOIN SEDE ON SEDE.ID_SEDE=P.ID_SEDE WHERE UPPER(P.NOME)=UPPER(?) AND UPPER(SEDE.INDIRIZZO)=UPPER(?))";
            PreparedStatement sql = conn.prepareStatement(query);
            sql.setString(1, strumento.getDescrizione());
            sql.setString(2, strumento.getSchedaTecnica());
            sql.setString(3, p.getNome());
            sql.setString(4, s.getIndirizzo());
            sql.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public List<Strumento> getStrumentoByDescription(String description) {
        List<Strumento> strumento = new ArrayList<>();
        try{
            String query = "SELECT * FROM STRUMENTO WHERE STRUMENTO.DESCRIZIONE LIKE ?";
            PreparedStatement sql = conn.prepareStatement(query);
            sql.setString(1, "'%"+description+"%'");
            ResultSet rs = sql.executeQuery();
            List<Postazione> postazioni = new PostazioneDao().getAll();
            while(rs.next()) {
                for(Postazione postazione:postazioni) {
                    if(rs.getString("STRUMENTO.ID_POSTAZIONE").equals(rs.getString("P.ID_POSTAZIONE")) && postazione.getNome().equals(rs.getString("NOME"))) {
                        Strumento s = new Strumento(postazione, rs.getString("DESCRIZIONE"), rs.getString("SCHEDATECNICA"));
                        strumento.add(s);

                    }
                }

            }
            rs.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return strumento;
    }

    public List<Strumento> getStrumentoBySede(Sede sede) {
        List<Strumento> strumento = new ArrayList<>();
        try{
            String query = "SELECT * FROM STRUMENTO JOIN POSTAZIONE P on STRUMENTO.ID_POSTAZIONE = P.ID_POSTAZIONE JOIN SEDE S on P.ID_SEDE = S.ID_SEDE WHERE UPPER(S.INDIRIZZO)=UPPER(?)";
            PreparedStatement sql = conn.prepareStatement(query);
            sql.setString(1, sede.getIndirizzo());
            ResultSet rs = sql.executeQuery();
            List<Postazione> postazioni = new PostazioneDao().getAll();
            while(rs.next()) {
                for(Postazione postazione:postazioni) {
                    if(rs.getString("STRUMENTO.ID_POSTAZIONE").equals(rs.getString("P.ID_POSTAZIONE")) && postazione.getNome().equals(rs.getString("NOME"))) {
                        Strumento s = new Strumento(postazione, rs.getString("DESCRIZIONE"), rs.getString("SCHEDATECNICA"));
                        strumento.add(s);

                    }
                }

            }
            rs.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return strumento;
    }
}
