package dao;

import java.util.List;

public interface Dao<T> {
    //Metodi
    public List<T> getAll();
    public void insert(T t);
    public void update(T t, List<String> params);
    public void delete(T t);
}
