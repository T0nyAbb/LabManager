package dao;

import DBconnection.OracleConnection;
import dto.Sede;
import dto.Tecnico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TecnicoDao implements Dao<Tecnico> {
    Connection conn = null;
    public TecnicoDao() {
        this.conn = OracleConnection.getOracleConnection().getConnection();
    }
    @Override
    public List<Tecnico> getAll() {
        List<Tecnico> tecnico = new ArrayList<>();
        try{
            String query = "SELECT * FROM TECNICO JOIN SEDE S on TECNICO.ID_SEDE = S.ID_SEDE";
            PreparedStatement sql = conn.prepareStatement(query);
            ResultSet rs = sql.executeQuery();
            List<Sede> sedi = new SedeDao().getAll();
            while(rs.next()) {
                for(Sede sede:sedi) {
                    if(rs.getString(10).equals(rs.getString(11)) && sede.getIndirizzo().equals(rs.getString(12))) {
                        Tecnico t = new Tecnico(sede, rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
                        tecnico.add(t);
                    }
                }

            }
            rs.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return tecnico;
    }

    @Override
    public void insert(Tecnico tecnico) {
        try{
            Sede s = tecnico.getSede();
            String query = "INSERT INTO TECNICO (MATRICOLA,NOME,COGNOME,DATANASCITA,CODICEFISCALE,INDIRIZZO,TELEFONO1,TELEFONO2,EMAIL,ID_SEDE) VALUES(?, ?, ?, DATE ?, ?, ?, ?, ?, ?, (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO)=UPPER(?) FETCH NEXT 1 ROWS ONLY))";
            PreparedStatement sql = conn.prepareStatement(query);
            sql.setString(1, tecnico.getMatricola());
            sql.setString(2, tecnico.getNome());
            sql.setString(3, tecnico.getCognome());
            sql.setString(4, tecnico.getDataNascita().toString());
            sql.setString(5, tecnico.getCodiceFiscale());
            sql.setString(6, tecnico.getIndirizzo());
            sql.setString(7, tecnico.getTelefono1());
            sql.setString(8, tecnico.getTelefono2());
            sql.setString(9, tecnico.getEmail());
            sql.setString(10, s.getIndirizzo());
            sql.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Tecnico tecnico, List<String> params) {
        try{
            if(params.size()!=9) throw new RuntimeException("numero di parametri non validi");
            String query = "UPDATE TECNICO SET MATRICOLA=?, NOME=?, COGNOME=?, DATANASCITA=DATE ?, CODICEFISCALE=?, INDIRIZZO=?, TELEFONO1=?, TELEFONO2=?, EMAIL=? WHERE MATRICOLA=?";
            PreparedStatement sql = conn.prepareStatement(query);
            sql.setString(1, params.get(1));
            sql.setString(2, params.get(2));
            sql.setString(3, params.get(3));
            sql.setString(4, params.get(4));
            sql.setString(5, params.get(5));
            sql.setString(6, params.get(6));
            sql.setString(7, params.get(7));
            sql.setString(8, params.get(8));
            sql.setString(9, params.get(9));
            sql.setString(10, tecnico.getMatricola());
            sql.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Tecnico tecnico) {
        try {
            String query = "DELETE FROM TECNICO WHERE MATRICOLA=?";
            PreparedStatement sql = conn.prepareStatement(query);
            sql.setString(1, tecnico.getMatricola());
            sql.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


}
