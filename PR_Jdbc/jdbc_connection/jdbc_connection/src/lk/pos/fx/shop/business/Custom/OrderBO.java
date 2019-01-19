package lk.pos.fx.shop.business.Custom;

import lk.pos.fx.shop.business.SuperBO;
import lk.pos.fx.shop.model.OrdersDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderBO extends SuperBO {
    public boolean saveOrders(OrdersDTO dto) throws ClassNotFoundException, SQLException;
    public boolean updateOrders(OrdersDTO dto)throws ClassNotFoundException, SQLException;
    public boolean deleteOrders(String code)throws ClassNotFoundException, SQLException;
    public OrdersDTO searchOrders(String code)throws ClassNotFoundException, SQLException;
    public ArrayList<OrdersDTO> getAllOrders()throws ClassNotFoundException, SQLException;
}
