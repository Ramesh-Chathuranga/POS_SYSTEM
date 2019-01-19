package lk.pos.fx.shop.dao;

import lk.pos.fx.shop.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    public static DAOFactory getInstance() {
        if(daoFactory==null){daoFactory=new DAOFactory();}
        return daoFactory;
    }

    public enum DAOType{
        CUSTOMER,ITEM,ORDER,ORDERDETAIL,USER,
    }

    public SuperDAO getDAO(DAOType type){
       switch (type){
           case CUSTOMER:
               return new CustomerDAOImpl();
           case ITEM:
               return new ItemDAOImpl();
           case ORDER:
               return new OrderDAOImpl();
           case ORDERDETAIL:
               return new OrderDetailDAOImpl();
           case USER:
               return new UserDAOImpl();
           default: return null;
       }
    }

    private DAOFactory() {
    }
}
