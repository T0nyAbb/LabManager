package dao;

import DBconnection.OracleConnection;
import dto.Postazione;
import dto.Sede;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PostazioneDao implements Dao<Postazione>{
    Connection conn = null;
    public PostazioneDao() {
        this.conn = OracleConnection.getOracleConnection().getConnection();
    }
    @Override
    public List<Postazione> getAll() {
        List<Postazione> postazione = new ArrayList<>();
        try{
            String query = "SELECT * FROM POSTAZIONE JOIN SEDE S on S.ID_SEDE = POSTAZIONE.ID_SEDE";
            PreparedStatement sql = conn.prepareStatement(query);
            ResultSet rs = sql.executeQuery();
            List<Sede> sedi = new SedeDao().getAll();
            while(rs.next()) {
                for(Sede sede:sedi) {
                    if(rs.getString(3).equals(rs.getString(4)) && sede.getIndirizzo().equals(rs.getString(5))) {
                        Postazione p = new Postazione(sede, rs.getString(2));
                        postazione.add(p);
                    }
                }

            }

        } catch(Exception e) {
            e.printStackTrace();
        }
        return postazione;
    }

    @Override
    public void insert(Postazione postazione) {
        try{
            Sede s = postazione.getSede();
            String query = "INSERT INTO POSTAZIONE(NOME, ID_SEDE) VALUES(?, (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO)=UPPER(?) FETCH NEXT 1 ROWS ONLY))";
            PreparedStatement sql = conn.prepareStatement(query);
            sql.setString(1, postazione.getNome());
            sql.setString(2, s.getIndirizzo());
            sql.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Postazione postazione, List<String> params) {
        try{
            if(params.size()!=1) throw new RuntimeException("numero di parametri non validi");
            Sede s = postazione.getSede();
            String query = "UPDATE POSTAZIONE SET NOME=? WHERE NOME=? AND ID_SEDE=(SELECT POSTAZIONE.ID_SEDE FROM SEDE JOIN POSTAZIONE ON SEDE.ID_SEDE=POSTAZIONE.ID_SEDE WHERE POSTAZIONE.NOME =? AND UPPER(SEDE.INDIRIZZO) = UPPER(?))";
            PreparedStatement sql = conn.prepareStatement(query);
            sql.setString(1, params.get(1));
            sql.setString(2, postazione.getNome());
            sql.setString(3, postazione.getNome());
            sql.setString(4, s.getIndirizzo());
            sql.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Postazione postazione) {
        try {
            Sede s = postazione.getSede();
            String query = "DELETE FROM POSTAZIONE WHERE NOME=? AND ID_SEDE=(SELECT POSTAZIONE.ID_SEDE FROM SEDE JOIN POSTAZIONE ON SEDE.ID_SEDE=POSTAZIONE.ID_SEDE WHERE POSTAZIONE.NOME =? AND UPPER(SEDE.INDIRIZZO) = UPPER(?))";
            PreparedStatement sql = conn.prepareStatement(query);
            sql.setString(1, postazione.getNome());
            sql.setString(2, postazione.getNome());
            sql.setString(3, s.getIndirizzo());
            sql.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
