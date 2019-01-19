package lk.pos.fx.shop.dao.custom.impl;

import lk.pos.fx.shop.dao.CrudUtil;
import lk.pos.fx.shop.dao.custom.UserDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {
   static String currentUser;

    @Override
    public boolean addUser(String name, String password) throws SQLException, ClassNotFoundException {
        String sql="INSERT INTO administrator VALUES(?,?)";

        int updateQuery = CrudUtil.updateQuery(sql, name, password);
        return updateQuery>0;
    }

    @Override
    public String getCurrentUser() {
        return currentUser;
    }

    @Override
    public boolean searchUser(String name) throws SQLException, ClassNotFoundException {
        String sql="SELECT * FROM administrator WHERE name= ?";
        ResultSet resultSet = CrudUtil.exequteQuery(sql, name);
        if(resultSet.next()){
          return true;
        }
        return false;
    }

    @Override
    public boolean getUserId(String name, String password) throws SQLException, ClassNotFoundException {
        String sql="SELECT * FROM administrator WHERE name= ? AND password =?";
        ResultSet resultSet = CrudUtil.exequteQuery(sql, name,password);
        if(resultSet.next()){
            currentUser=resultSet.getString(1);
            return true;
        }
        return false;

    }

    @Override
    public void addCurrentUser(String userName) {
        this.currentUser=userName;
    }


}
