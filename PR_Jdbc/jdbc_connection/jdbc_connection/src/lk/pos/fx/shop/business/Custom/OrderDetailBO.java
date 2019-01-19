package lk.pos.fx.shop.business.Custom;

import lk.pos.fx.shop.business.SuperBO;
import lk.pos.fx.shop.model.OrderDetailDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailBO extends SuperBO {
    public boolean saveOrderDetail(ArrayList<OrderDetailDTO> details) throws SQLException, ClassNotFoundException;
    public ArrayList<OrderDetailDTO> getAllOdetails()throws SQLException, ClassNotFoundException;;
    public ArrayList<OrderDetailDTO> getODetailByOID(String oID)throws SQLException, ClassNotFoundException;
}
