package lk.pos.fx.shop.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomDAO<T,ID> extends SuperDAO {
    public boolean save(T object) throws ClassNotFoundException, SQLException;
    public boolean update(T object)throws ClassNotFoundException, SQLException;
    public boolean delete(ID code)throws ClassNotFoundException, SQLException;
    public T search(ID code)throws ClassNotFoundException, SQLException;
    public ArrayList<T> getAll()throws ClassNotFoundException, SQLException;
}
