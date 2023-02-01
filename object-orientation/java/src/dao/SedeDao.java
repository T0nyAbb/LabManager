package dao;

import dto.Laboratorio;
import dto.Sede;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SedeDao implements Dao<Sede>{
    private Connection conn;
    
    public SedeDao(Connection conn) {
        this.conn = conn;
    }
    
    @Override
    public List<Sede> getAll() throws SQLException {
        List<Sede> sede = new ArrayList<>();
        String query = "SELECT * FROM SEDE JOIN LABORATORIO L on L.ID_LAB = SEDE.ID_LAB";
        PreparedStatement sql = conn.prepareStatement(query);
        ResultSet rs = sql.executeQuery();
        List<Laboratorio> labs = new LaboratorioDao(conn).getAll();
        while(rs.next()) {
            for(Laboratorio lab:labs) {
                if(lab.getId() == rs.getInt("ID_LAB")) {
                    Sede s = new Sede(rs.getString("INDIRIZZO"), lab);
                    s.setId(rs.getInt("ID_SEDE"));
                    sede.add(s);
                }

            }

        }
        rs.close();
        sql.close();
        return sede;
    }


    @Override
    public void insert(Sede sede) throws SQLException {
        Laboratorio lab = sede.getLaboratorio();
        String query = "INSERT INTO SEDE(INDIRIZZO, ID_LAB) VALUES(?, (SELECT ID_LAB FROM LABORATORIO WHERE UPPER(LABORATORIO.NOME) = UPPER(?) FETCH NEXT 1 ROWS ONLY))";
        PreparedStatement sql = conn.prepareStatement(query);
        sql.setString(1, sede.getIndirizzo());
        sql.setString(2, lab.getNome());
        sql.executeUpdate();
        sql.close();

    }

    @Override
    public void update(Sede sede, List<String> params) throws SQLException {
        if(params.size()!=1) throw new RuntimeException("numero di parametri non validi");

        String query = "UPDATE SEDE SET INDIRIZZO=? WHERE INDIRIZZO =?";
        PreparedStatement sql = conn.prepareStatement(query);
        sql.setString(1, params.get(1));
        sql.setString(2, sede.getIndirizzo());
        sql.executeUpdate();
        sql.close();
    }

    @Override
    public void delete(Sede sede) throws SQLException {
        String query = "DELETE FROM SEDE WHERE INDIRIZZO=?";
        PreparedStatement sql = conn.prepareStatement(query);
        sql.setString(1, sede.getIndirizzo());
        sql.executeUpdate();
        sql.close();
    }

    public List<Sede> getSedeByLab(Laboratorio lab) throws SQLException {
        List<Sede> sede = new ArrayList<>();
        String query = "SELECT * FROM SEDE JOIN LABORATORIO ON SEDE.ID_LAB=LABORATORIO.ID_LAB WHERE LABORATORIO.NOME=? AND LABORATORIO.ANNOFONDAZIONE=?";
        PreparedStatement sql = conn.prepareStatement(query);
        sql.setString(1, lab.getNome());
        sql.setInt(2, lab.getAnnoFondazione());
        ResultSet rs = sql.executeQuery();

        while (rs.next()) {
            Sede s = new Sede(rs.getString("INDIRIZZO"));
            sede.add(s);
        }
        
        rs.close();
        sql.close();
        return sede;
    }

}

