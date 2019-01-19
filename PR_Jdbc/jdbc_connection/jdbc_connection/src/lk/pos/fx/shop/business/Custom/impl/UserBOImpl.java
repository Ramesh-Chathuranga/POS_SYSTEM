package lk.pos.fx.shop.business.Custom.impl;

import lk.pos.fx.shop.business.Custom.UserBO;
import lk.pos.fx.shop.dao.DAOFactory;
import lk.pos.fx.shop.dao.custom.UserDAO;

import java.sql.SQLException;

public class UserBOImpl implements UserBO {

    UserDAO userDAO=(UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER);
    @Override
    public boolean addUser(String name, String password) throws SQLException, ClassNotFoundException {
        return userDAO.addUser(name,password);
    }

    @Override
    public String getCurrentUser() {
        return userDAO.getCurrentUser();
    }

    @Override
    public boolean searchUser(String name) throws SQLException, ClassNotFoundException {
        return userDAO.searchUser(name);
    }

    @Override
    public boolean getUserId(String name, String password) throws SQLException, ClassNotFoundException {
        return userDAO.getUserId(name,password);
    }

    @Override
    public void addCurrentUser(String user) {
        userDAO.addCurrentUser(user);
    }


}
