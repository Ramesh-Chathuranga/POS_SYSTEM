package lk.pos.fx.shop.business.Custom;

import lk.pos.fx.shop.business.SuperBO;

import java.sql.SQLException;

public interface UserBO extends SuperBO {
    public boolean addUser(String name,String password)throws SQLException,ClassNotFoundException;
    public String getCurrentUser();
    public boolean searchUser(String name)throws SQLException,ClassNotFoundException;
    public boolean getUserId(String name,String password)throws SQLException,ClassNotFoundException;
    public void addCurrentUser(String user);

}
