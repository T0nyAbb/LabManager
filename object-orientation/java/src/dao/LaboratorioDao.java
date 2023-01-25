package dao;

import DBconnection.OracleConnection;
import dto.Laboratorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LaboratorioDao implements Dao<Laboratorio>{
    Connection conn = null;
    public LaboratorioDao() {
        this.conn = OracleConnection.getOracleConnection().getConnection();
    }
    @Override
    public List<Laboratorio> getAll() {
        List<Laboratorio> laboratorio = new ArrayList<>();
        try{
            String query = "SELECT * FROM LABORATORIO";
            PreparedStatement sql = conn.prepareStatement(query);
            ResultSet rs = sql.executeQuery();

            while(rs.next()) {
                Laboratorio lab = new Laboratorio(rs.getString("NOME"), rs.getInt("ANNOFONDAZIONE"), rs.getString("CAMPO"), rs.getString("DESCRIZIONE"));
                laboratorio.add(lab);
            }
            rs.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return laboratorio;
    }

    @Override
    public void insert(Laboratorio laboratorio) {
    try{
        String query = "INSERT INTO LABORATORIO(NOME, DESCRIZIONE, CAMPO, ANNOFONDAZIONE) VALUES(?,?,?,?)";
        PreparedStatement sql = conn.prepareStatement(query);
        sql.setString(1, laboratorio.getNome());
        sql.setString(2, laboratorio.getDescrizione());
        sql.setString(3, laboratorio.getCampo());
        sql.setInt(4, laboratorio.getAnnoFondazione());
        sql.executeUpdate();
    } catch(Exception e) {
        e.printStackTrace();
    }
    }

    @Override
    public void update(Laboratorio laboratorio, List<String> params) {
        try{
            if(params.size()!=4) throw new RuntimeException("numero di parametri non validi");

            String query = "UPDATE LABORATORIO SET NOME=?, DESCRIZIONE=?, CAMPO=?, ANNOFONDAZIONE=? WHERE NOME =? AND ANNOFONDAZIONE =?";
            PreparedStatement sql = conn.prepareStatement(query);
            sql.setString(1, params.get(1));
            sql.setString(2, params.get(2));
            sql.setString(3, params.get(3));
            sql.setString(4, params.get(4));
            sql.setString(5, laboratorio.getNome());
            sql.setInt(6, laboratorio.getAnnoFondazione());
            sql.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Laboratorio laboratorio) {
        try {
            String query = "DELETE FROM LABORATORIO WHERE NOME=? AND ANNOFONDAZIONE=?";
            PreparedStatement sql = conn.prepareStatement(query);
            sql.setString(1, laboratorio.getNome());
            sql.setInt(2, laboratorio.getAnnoFondazione());
            sql.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}
