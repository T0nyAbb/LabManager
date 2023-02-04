package dao;

import dto.Postazione;
import dto.Sede;
import dto.Strumento;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class StrumentoDao implements Dao<Strumento> {
    private Connection conn;
    
    public StrumentoDao(Connection conn) {
        this.conn = conn;
    }
    
    @Override
    public List<Strumento> getAll() throws SQLException {
        List<Strumento> strumento = new ArrayList<>();
        String query = "SELECT * FROM STRUMENTO JOIN POSTAZIONE P on P.ID_POSTAZIONE = STRUMENTO.ID_POSTAZIONE";
        PreparedStatement sql = conn.prepareStatement(query);
        ResultSet rs = sql.executeQuery();
        List<Postazione> postazioni = new PostazioneDao(conn).getAll();
        while(rs.next()) {
            for(Postazione postazione:postazioni) {
                if(postazione.getId() == rs.getInt("ID_POSTAZIONE")) {
                    Strumento s = new Strumento(postazione, rs.getString("DESCRIZIONE"), rs.getString("SCHEDATECNICA"));
                    s.setId(rs.getInt("ID_STRUMENTO"));
                    strumento.add(s);
                }
            }

        }
        rs.close();
        sql.close();
        return strumento;
    }


    @Override
    public void insert(Strumento strumento) throws SQLException {
        Postazione p = strumento.getPostazione();
        Sede s = p.getSede();
        String query = "INSERT INTO STRUMENTO(DESCRIZIONE, SCHEDATECNICA,  ID_POSTAZIONE) VALUES(?, ?,(SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(POSTAZIONE.NOME)=UPPER(?) AND UPPER(SEDE.INDIRIZZO)=UPPER(?) FETCH NEXT 1 ROWS ONLY))";
        PreparedStatement sql = conn.prepareStatement(query);
        sql.setString(1, strumento.getDescrizione());
        sql.setString(2, strumento.getSchedaTecnica());
        sql.setString(3, p.getNome());
        sql.setString(4, s.getIndirizzo());
        sql.executeUpdate();
        sql.close();

    }

    @Override
    public void update(Strumento strumento, List<String> params) throws SQLException {
        if(params.size()!=2) throw new RuntimeException("numero di parametri non validi");
        Postazione p = strumento.getPostazione();
        Sede s = p.getSede();
        String query = "UPDATE STRUMENTO SET DESCRIZIONE=?, SCHEDATECNICA=? WHERE UPPER(DESCRIZIONE)=UPPER(?)"
        		+ "AND UPPER(SCHEDATECNICA)=UPPER(?) AND ID_STRUMENTO=(SELECT ID_STRUMENTO FROM STRUMENTO S JOIN"
        		+ "POSTAZIONE P ON S.ID_POSTAZIONE=P.ID_POSTAZIONE JOIN SEDE ON SEDE.ID_SEDE=P.ID_SEDE"
        		+ "WHERE UPPER(P.NOME)=UPPER(?) AND UPPER(SEDE.INDIRIZZO)=UPPER(?))";
        PreparedStatement sql = conn.prepareStatement(query);
        sql.setString(1, params.get(1));
        sql.setString(2, params.get(2));
        sql.setString(3, strumento.getDescrizione());
        sql.setString(4, strumento.getSchedaTecnica());
        sql.setString(5, p.getNome());
        sql.setString(6, s.getIndirizzo());
        sql.executeUpdate();
        sql.close();
    }

    @Override
    public void delete(Strumento strumento) throws SQLException {
        Postazione p = strumento.getPostazione();
        Sede s = p.getSede();
        String query = "DELETE FROM STRUMENTO WHERE UPPER(DESCRIZIONE)=UPPER(?) AND UPPER(SCHEDATECNICA)=UPPER(?)"
        		+ "AND ID_STRUMENTO=(SELECT ID_STRUMENTO FROM STRUMENTO S JOIN POSTAZIONE P ON"
        		+ "S.ID_POSTAZIONE=P.ID_POSTAZIONE JOIN SEDE ON SEDE.ID_SEDE=P.ID_SEDE WHERE UPPER(P.NOME)=UPPER(?)"
        		+ "AND UPPER(SEDE.INDIRIZZO)=UPPER(?))";
        PreparedStatement sql = conn.prepareStatement(query);
        sql.setString(1, strumento.getDescrizione());
        sql.setString(2, strumento.getSchedaTecnica());
        sql.setString(3, p.getNome());
        sql.setString(4, s.getIndirizzo());
        sql.executeUpdate();
        sql.close();
    }
    
    public List<Strumento> getStrumentoByDescription(String description) throws SQLException {
        List<Strumento> strumento = new ArrayList<>();
        String query = "SELECT * FROM STRUMENTO JOIN POSTAZIONE P on P.ID_POSTAZIONE = STRUMENTO.ID_POSTAZIONE JOIN SEDE S on P.ID_SEDE = S.ID_SEDE WHERE UPPER(STRUMENTO.DESCRIZIONE) LIKE UPPER(?)";
        PreparedStatement sql = conn.prepareStatement(query);
        sql.setString(1, "%"+description+"%");
        ResultSet rs = sql.executeQuery();
        List<Postazione> postazioni = new PostazioneDao(conn).getAll();
        while(rs.next()) {
            for(Postazione postazione:postazioni) {
                Sede sede = postazione.getSede();
                if(rs.getString(4).equals(rs.getString(5)) && postazione.getNome().equals(rs.getString(6)) && sede.getIndirizzo().equals(rs.getString(9))) {
                    Strumento s = new Strumento(postazione, rs.getString(2), rs.getString(3));
                    strumento.add(s);
                }
            }
        }
        rs.close();
        sql.close();
        return strumento;
    }

    public List<Strumento> getStrumentoBySede(Sede sede) throws SQLException {
        List<Strumento> strumento = new ArrayList<>();
        String query = "SELECT * FROM STRUMENTO JOIN POSTAZIONE P on STRUMENTO.ID_POSTAZIONE = P.ID_POSTAZIONE JOIN SEDE S on P.ID_SEDE = S.ID_SEDE WHERE S.ID_SEDE=?";
        PreparedStatement sql = conn.prepareStatement(query);
        sql.setInt(1, sede.getId());
        ResultSet rs = sql.executeQuery();
        List<Postazione> postazioni = new PostazioneDao(conn).getAll();
        while(rs.next()) {
            for(Postazione postazione:postazioni) {
                if(postazione.getId() == rs.getInt("ID_POSTAZIONE")) {
                    Strumento s = new Strumento(postazione, rs.getString("DESCRIZIONE"), rs.getString("SCHEDATECNICA"));
                    s.setId(rs.getInt("ID_STRUMENTO"));
                    strumento.add(s);

                }
            }

        }
        rs.close();
        sql.close();
        return strumento;
    }
    public List<String> getAvailableMonthForStats(Strumento strumento) throws SQLException {
        SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MMM", Locale.ITALY);
        List<String> mesiDisponibili = new ArrayList<>();
        //La query prende solo i mesi che sono già passati
        String query = "SELECT MESE FROM RIEPILOGO_UTILIZZO_STRUMENTO_MESE WHERE MESE<(SELECT SYSDATE FROM DUAL) AND ID_STRUMENTO=?";
        PreparedStatement sql = conn.prepareStatement(query);
        sql.setInt(1, strumento.getId());
        ResultSet rs = sql.executeQuery();
        while (rs.next()) {
            String mese = monthFormat.format(rs.getTimestamp(1));
            mesiDisponibili.add(mese);
        }
        rs.close();
        sql.close();
        return mesiDisponibili;
    }
    public String getStatsByMese(Strumento strumento, String mese) throws SQLException {
        String query = "SELECT * FROM RIEPILOGO_UTILIZZO_STRUMENTO_MESE R JOIN TOP_UTILIZZATORE_PER_STRUMENTO_MESE T ON R.ID_STRUMENTO=T.ID_STRUMENTO WHERE R.MESE = TO_DATE(?, 'YYYY/MON') AND R.ID_STRUMENTO=?";
        PreparedStatement sql = conn.prepareStatement(query);
        sql.setString(1, mese);
        sql.setInt(2, strumento.getId());
        ResultSet rs = sql.executeQuery();
        if(rs.next()) {
            return "Lo strumento durante il mese è stato utilizzato per un totale di " + rs.getInt(4) + " ore. L'utente che lo ha utilizzato di più è stato " + rs.getString(7) + " con ben " + rs.getInt(8) + " ore di utilizzo";
        } else {
            return "Non sono disponibili statistiche per il mese selezionato";
        }
    }
}
