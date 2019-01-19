package lk.pos.fx.shop.dao.custom;



import lk.pos.fx.shop.dao.SuperDAO;

import java.sql.SQLException;


public interface UserDAO extends SuperDAO {
    public boolean addUser(String name,String password)throws SQLException,ClassNotFoundException;
    public String getCurrentUser();
    public boolean searchUser(String name)throws SQLException,ClassNotFoundException;
    public  boolean getUserId(String name,String password)throws SQLException,ClassNotFoundException;
    public void addCurrentUser(String userName);

}
