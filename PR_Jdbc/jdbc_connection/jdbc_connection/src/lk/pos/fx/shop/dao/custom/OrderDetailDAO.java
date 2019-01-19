package lk.pos.fx.shop.dao.custom;

import lk.pos.fx.shop.dao.CustomDAO;
import lk.pos.fx.shop.dao.SuperDAO;
import lk.pos.fx.shop.entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailDAO extends SuperDAO {
    public boolean save(ArrayList<OrderDetail>details) throws SQLException, ClassNotFoundException;
    public ArrayList<OrderDetail> getAllOdetails()throws SQLException, ClassNotFoundException;;
    public ArrayList<OrderDetail> getODetailByOID(String oID)throws SQLException, ClassNotFoundException;;
}
