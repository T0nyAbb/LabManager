package dao;

import dto.Responsabile;
import dto.Sede;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResponsabileDao implements Dao<Responsabile> {
    private Connection conn;
    
    public ResponsabileDao(Connection conn) {
        this.conn = conn;
    }
    
    @Override
    public List<Responsabile> getAll() throws SQLException {
        List<Responsabile> responsabile = new ArrayList<>();
        String query = "SELECT * FROM RESPONSABILE JOIN SEDE S ON RESPONSABILE.ID_SEDE = S.ID_SEDE";
        PreparedStatement sql = conn.prepareStatement(query);
        ResultSet rs = sql.executeQuery();
        List<Sede> sedi = new SedeDao(conn).getAll();
        while(rs.next()) {
            for(Sede sede:sedi) {
                if(rs.getString(10).equals(rs.getString(11)) && sede.getIndirizzo().equals(rs.getString(12))) {
                    Responsabile r = new Responsabile(sede, rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
                    responsabile.add(r);
                }
            }

        }
        rs.close();
        sql.close();
        return responsabile;
    }

    @Override
    public void insert(Responsabile responsabile) throws SQLException {
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
        sql.close();

    }

    @Override
    public void update(Responsabile responsabile, List<String> params) throws SQLException {
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
        sql.close();
    }

    @Override
    public void delete(Responsabile responsabile) throws SQLException {
        String query = "DELETE FROM RESPONSABILE WHERE MATRICOLA=?";
        PreparedStatement sql = conn.prepareStatement(query);
        sql.setString(1, responsabile.getMatricola());
        sql.executeUpdate();
        sql.close();
    }
}
