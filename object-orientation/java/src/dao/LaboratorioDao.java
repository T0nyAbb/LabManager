package dao;

import dto.Laboratorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LaboratorioDao implements Dao<Laboratorio>{
    private Connection conn;
    
    public LaboratorioDao(Connection conn) {
        this.conn = conn;
    }
    
    @Override
    public List<Laboratorio> getAll() throws SQLException{
        List<Laboratorio> laboratorio = new ArrayList<>();
        String query = "SELECT * FROM LABORATORIO";
        PreparedStatement sql = conn.prepareStatement(query);
        ResultSet rs = sql.executeQuery();
        while(rs.next()) {
            Laboratorio lab = new Laboratorio(rs.getString("NOME"), rs.getInt("ANNOFONDAZIONE"), rs.getString("CAMPO"), rs.getString("DESCRIZIONE"));
            lab.setId(rs.getInt("ID_LAB"));
            laboratorio.add(lab);
        }
        rs.close();
        sql.close();
        return laboratorio;
    }

    @Override
    public void insert(Laboratorio laboratorio) throws SQLException {
        String query = "INSERT INTO LABORATORIO(NOME, DESCRIZIONE, CAMPO, ANNOFONDAZIONE) VALUES(?,?,?,?)";
        PreparedStatement sql = conn.prepareStatement(query);
        sql.setString(1, laboratorio.getNome());
        sql.setString(2, laboratorio.getDescrizione());
        sql.setString(3, laboratorio.getCampo());
        sql.setInt(4, laboratorio.getAnnoFondazione());
        sql.executeUpdate();
        sql.close();
    }

    @Override
    public void update(Laboratorio laboratorio, List<String> params) throws SQLException {
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
        sql.close();

    }

    @Override
    public void delete(Laboratorio laboratorio) throws SQLException {
        String query = "DELETE FROM LABORATORIO WHERE NOME=? AND ANNOFONDAZIONE=?";
        PreparedStatement sql = conn.prepareStatement(query);
        sql.setString(1, laboratorio.getNome());
        sql.setInt(2, laboratorio.getAnnoFondazione());
        sql.executeUpdate();
        sql.close();

    }
}
