package dao;

import DBconnection.OracleConnection;
import dto.Responsabile;
import dto.Sede;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ResponsabileDao implements Dao<Responsabile> {
    Connection conn = null;
    public ResponsabileDao() {
        this.conn = OracleConnection.getOracleConnection().getConnection();
    }
    @Override
    public List<Responsabile> getAll() {
        List<Responsabile> responsabile = new ArrayList<>();
        try{
            String query = "SELECT * FROM RESPONSABILE JOIN SEDE S ON RESPONSABILE.ID_SEDE = S.ID_SEDE";
            PreparedStatement sql = conn.prepareStatement(query);
            ResultSet rs = sql.executeQuery();
            List<Sede> sedi = new SedeDao().getAll();
            while(rs.next()) {
                for(Sede sede:sedi) {
                    if(rs.getString("RESPONSABILE.ID_SEDE").equals(rs.getString("S.ID_SEDE")) && sede.getIndirizzo().equals(rs.getString("S.INDIRIZZO"))) {
                        Responsabile r = new Responsabile(sede, rs.getString("MATRICOLA"), rs.getString("NOME"), rs.getString("COGNOME"), rs.getDate("DATANASCITA"), rs.getString("CODICEFISCALE"), rs.getString("TECNICO.INDIRIZZO"), rs.getString("TELEFONO1"), rs.getString("TELEFONO2"), rs.getString("EMAIL"));
                        responsabile.add(r);
                    }
                }

            }
            rs.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return responsabile;
    }

    @Override
    public void insert(Responsabile responsabile) {
        try{
            Sede s = responsabile.getSede();
            String query = "INSERT INTO RESPONSABILE (MATRICOLA,NOME,COGNOME,DATANASCITA,CODICEFISCALE,INDIRIZZO,TELEFONO1,TELEFONO2,EMAIL,ID_SEDE) VALUES(?, ?, ?, DATE ?, ?, ?, ?, ?, ?, (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO)=UPPER(?) FETCH NEXT 1 ROWS ONLY))";
            PreparedStatement sql = conn.prepareStatement(query);
            sql.setString(1, responsabile.getMatricola());
            sql.setString(2, responsabile.getNome());
            sql.setString(3, responsabile.getCognome());
            sql.setString(4, responsabile.getDataNascita().toString());
            sql.setString(5, responsabile.getCodiceFiscale());
            sql.setString(6, responsabile.getIndirizzo());
            sql.setString(7, responsabile.getTelefono1());
            sql.setString(8, responsabile.getTelefono2());
            sql.setString(9, responsabile.getEmail());
            sql.setString(10, s.getIndirizzo());
            sql.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Responsabile responsabile, List<String> params) {
        try {
            if (params.size() != 9) throw new RuntimeException("numero di parametri non validi");
            String query = "UPDATE RESPONSABILE SET MATRICOLA=?, NOME=?, COGNOME=?, DATANASCITA=DATE ?, CODICEFISCALE=?, INDIRIZZO=?, TELEFONO1=?, TELEFONO2=?, EMAIL=? WHERE MATRICOLA=?";
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
            sql.setString(10, responsabile.getMatricola());
            sql.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Responsabile responsabile) {
        try {
            String query = "DELETE FROM RESPONSABILE WHERE MATRICOLA=?";
            PreparedStatement sql = conn.prepareStatement(query);
            sql.setString(1, responsabile.getMatricola());
            sql.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}
