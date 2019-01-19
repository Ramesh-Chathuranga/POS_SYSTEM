package lk.pos.fx.shop.business.Custom.impl;

import lk.pos.fx.shop.business.BOFactory;
import lk.pos.fx.shop.business.Custom.OrderBO;
import lk.pos.fx.shop.business.Custom.OrderDetailBO;
import lk.pos.fx.shop.dao.DAOFactory;
import lk.pos.fx.shop.dao.custom.OrdersDAO;
import lk.pos.fx.shop.entity.OrderDetail;
import lk.pos.fx.shop.entity.Orders;
import lk.pos.fx.shop.model.OrderDetailDTO;
import lk.pos.fx.shop.model.OrdersDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBOImpl implements OrderBO {
    OrdersDAO dao=(OrdersDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER);
    OrderDetailBO bo=(OrderDetailBO) BOFactory.getInstance().getBo(BOFactory.BOType.ORDERDETAIL);
    @Override
    public boolean saveOrders(OrdersDTO dto) throws ClassNotFoundException, SQLException {
        ArrayList<OrderDetail>details=new ArrayList<>();
        for (OrderDetailDTO dto1:dto.getOrderDetailDTOS()) {
            details.add(new OrderDetail(dto1.getOrderId(),dto1.getItemcode(),dto1.getDescription(),dto1.getQty(),dto1.getUnitPrice()));
        }
        boolean isDetaiSave = bo.saveOrderDetail(dto.getOrderDetailDTOS());
        boolean isOrderSave = dao.save(new Orders(dto.getOrderId(), dto.getDate(), dto.getCustomerId(), details));
        return (isDetaiSave&&isOrderSave);
    }

    @Override
    public boolean updateOrders(OrdersDTO dto) throws ClassNotFoundException, SQLException {
        ArrayList<OrderDetail>details=new ArrayList<>();
        for (OrderDetailDTO dto1:dto.getOrderDetailDTOS()) {
            details.add(new OrderDetail(dto1.getOrderId(),dto1.getItemcode(),dto1.getDescription(),dto1.getQty(),dto1.getUnitPrice()));
        }

        return dao.update(new Orders(dto.getOrderId(),dto.getDate(),dto.getCustomerId(),details));
    }

    @Override
    public boolean deleteOrders(String code) throws ClassNotFoundException, SQLException {
        return dao.delete(code);
    }

    @Override
    public OrdersDTO searchOrders(String code) throws ClassNotFoundException, SQLException {
        Orders orders = dao.search(code);
        ArrayList<OrderDetailDTO>list=new ArrayList<>();
        for (OrderDetail dto1:orders.getOrderDetails()) {
            list.add(new OrderDetailDTO(dto1.getOrderId(),dto1.getItemcode(),dto1.getDescription(),dto1.getQty(),dto1.getUnitPrice()));
        }
        return new OrdersDTO(orders.getOrderId(),orders.getDate(),orders.getCustomerId(),list);
    }

    @Override
    public ArrayList<OrdersDTO> getAllOrders() throws ClassNotFoundException, SQLException {
        ArrayList<Orders> all = dao.getAll();
        ArrayList<OrdersDTO>dtos=new ArrayList<>();
        for (Orders orders :all) {
            ArrayList<OrderDetailDTO>list=new ArrayList<>();
            for (OrderDetail dto1:orders.getOrderDetails()) {
                list.add(new OrderDetailDTO(dto1.getOrderId(),dto1.getItemcode(),dto1.getDescription(),dto1.getQty(),dto1.getUnitPrice()));
            }
            dtos.add(new OrdersDTO(orders.getOrderId(),orders.getDate(),orders.getCustomerId(),list));
        }
        return dtos;
    }
}
