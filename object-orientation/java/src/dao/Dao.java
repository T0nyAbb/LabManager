package dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {
	
    //Metodi
    public List<T> getAll() throws SQLException;
    public void insert(T t) throws SQLException;
    public void update(T t, List<String> params) throws SQLException;
    public void delete(T t) throws SQLException;
}
