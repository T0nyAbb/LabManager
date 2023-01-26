package dao;

import DBconnection.OracleConnection;
import dto.Laboratorio;
import dto.Sede;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SedeDao implements Dao<Sede>{
    Connection conn = null;
    public SedeDao() {
        this.conn = OracleConnection.getOracleConnection().getConnection();
    }
    @Override
    public List<Sede> getAll() {
        List<Sede> sede = new ArrayList<>();
        try{
            String query = "SELECT * FROM SEDE JOIN LABORATORIO L on L.ID_LAB = SEDE.ID_LAB";
            PreparedStatement sql = conn.prepareStatement(query);
            ResultSet rs = sql.executeQuery();
            List<Laboratorio> labs = new LaboratorioDao().getAll();
            while(rs.next()) {
                for(Laboratorio lab:labs) {
                    if(rs.getString(3).equals(rs.getString(4)) && lab.getNome().equals(rs.getString(5))) {
                        Sede s = new Sede(rs.getString(2), lab);
                        sede.add(s);
                    }

                }

            }

        } catch(Exception e) {
            e.printStackTrace();
        }
        return sede;
    }


    @Override
    public void insert(Sede sede) {
        try{
            Laboratorio lab = sede.getLaboratorio();
            String query = "INSERT INTO SEDE(INDIRIZZO, ID_LAB) VALUES(?, (SELECT ID_LAB FROM LABORATORIO WHERE UPPER(LABORATORIO.NOME) = UPPER(?) FETCH NEXT 1 ROWS ONLY))";
            PreparedStatement sql = conn.prepareStatement(query);
            sql.setString(1, sede.getIndirizzo());
            sql.setString(2, lab.getNome());
            sql.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Sede sede, List<String> params) {
        try{
            if(params.size()!=1) throw new RuntimeException("numero di parametri non validi");

            String query = "UPDATE SEDE SET INDIRIZZO=? WHERE INDIRIZZO =?";
            PreparedStatement sql = conn.prepareStatement(query);
            sql.setString(1, params.get(1));
            sql.setString(2, sede.getIndirizzo());
            sql.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Sede sede) {
        try {
            String query = "DELETE FROM SEDE WHERE INDIRIZZO=?";
            PreparedStatement sql = conn.prepareStatement(query);
            sql.setString(1, sede.getIndirizzo());
            sql.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public List<Sede> getSedeByLab(Laboratorio lab) {
        List<Sede> sede = new ArrayList<>();
        try {
            String query = "SELECT * FROM SEDE JOIN LABORATORIO ON SEDE.ID_LAB=LABORATORIO.ID_LAB WHERE LABORATORIO.NOME=? AND LABORATORIO.ANNOFONDAZIONE=?";
            PreparedStatement sql = conn.prepareStatement(query);
            sql.setString(1, lab.getNome());
            sql.setInt(2, lab.getAnnoFondazione());
            ResultSet rs = sql.executeQuery();

            while (rs.next()) {
                Sede s = new Sede(rs.getString("INDIRIZZO"));
                sede.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sede;

    }

}

