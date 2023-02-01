package dao;

import dto.Postazione;
import dto.Sede;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostazioneDao implements Dao<Postazione>{
	private Connection conn;
	
    public PostazioneDao(Connection conn) {
    	this.conn = conn;
    }
    
    @Override
    public List<Postazione> getAll() throws SQLException {
        List<Postazione> postazione = new ArrayList<>();
        String query = "SELECT * FROM POSTAZIONE JOIN SEDE S on S.ID_SEDE = POSTAZIONE.ID_SEDE";
        PreparedStatement sql = conn.prepareStatement(query);
        ResultSet rs = sql.executeQuery();
        List<Sede> sedi = new SedeDao(conn).getAll();
        while(rs.next()) {
            for(Sede sede:sedi) {
                if(sede.getId() == rs.getInt("ID_SEDE")) {
                    Postazione p = new Postazione(sede, rs.getString("NOME"));
                    p.setId(rs.getInt("ID_POSTAZIONE"));
                    postazione.add(p);
                }
            }

        }
        sql.close();
        return postazione;
    }

    @Override
    public void insert(Postazione postazione) throws SQLException {
            Sede s = postazione.getSede();
            String query = "INSERT INTO POSTAZIONE(NOME, ID_SEDE) VALUES(?, (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO)=UPPER(?) FETCH NEXT 1 ROWS ONLY))";
            PreparedStatement sql = conn.prepareStatement(query);
            sql.setString(1, postazione.getNome());
            sql.setString(2, s.getIndirizzo());
            sql.executeUpdate();
    }

    @Override
    public void update(Postazione postazione, List<String> params) throws SQLException {
            if(params.size()!=1) throw new RuntimeException("numero di parametri non validi");
            Sede s = postazione.getSede();
            String query = "UPDATE POSTAZIONE SET NOME=? WHERE NOME=? AND ID_SEDE=(SELECT POSTAZIONE.ID_SEDE FROM SEDE JOIN POSTAZIONE ON SEDE.ID_SEDE=POSTAZIONE.ID_SEDE WHERE POSTAZIONE.NOME =? AND UPPER(SEDE.INDIRIZZO) = UPPER(?))";
            PreparedStatement sql = conn.prepareStatement(query);            sql.setString(1, params.get(1));
            sql.setString(2, postazione.getNome());
            sql.setString(3, postazione.getNome());
            sql.setString(4, s.getIndirizzo());
            sql.executeUpdate();
            sql.close();

    }

    @Override
    public void delete(Postazione postazione) throws SQLException {
        Sede s = postazione.getSede();
        String query = "DELETE FROM POSTAZIONE WHERE NOME=? AND ID_SEDE=(SELECT POSTAZIONE.ID_SEDE FROM SEDE JOIN POSTAZIONE ON SEDE.ID_SEDE=POSTAZIONE.ID_SEDE WHERE POSTAZIONE.NOME =? AND UPPER(SEDE.INDIRIZZO) = UPPER(?))";
        PreparedStatement sql = conn.prepareStatement(query);
        sql.setString(1, postazione.getNome());
        sql.setString(2, postazione.getNome());
        sql.setString(3, s.getIndirizzo());
        sql.executeUpdate();
        sql.close();
        
    }
}
