package lk.pos.fx.shop.business.Custom.impl;

import lk.pos.fx.shop.business.Custom.OrderDetailBO;
import lk.pos.fx.shop.dao.DAOFactory;
import lk.pos.fx.shop.dao.custom.OrderDetailDAO;
import lk.pos.fx.shop.entity.OrderDetail;
import lk.pos.fx.shop.model.OrderDetailDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailBOImpl implements OrderDetailBO {
    OrderDetailDAO dto=(OrderDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDERDETAIL);
    @Override
    public boolean saveOrderDetail(ArrayList<OrderDetailDTO> details) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetail> list=new ArrayList();
        for (OrderDetailDTO dto :details) {
            list.add(new OrderDetail(dto.getOrderId(),dto.getItemcode(),dto.getDescription(),dto.getQty(),dto.getUnitPrice()));
        }
        System.out.println(details.size()+"    this BO");
       return dto.save(list);


    }

    @Override
    public ArrayList<OrderDetailDTO> getAllOdetails() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetail> list = dto.getAllOdetails();
        ArrayList<OrderDetailDTO>dtos=new ArrayList<>();
        for (OrderDetail detail:list) {
            dtos.add(new OrderDetailDTO(detail.getOrderId(),detail.getItemcode(),detail.getDescription(),detail.getQty(),detail.getUnitPrice()));
        }
        return dtos;
    }

    @Override
    public ArrayList<OrderDetailDTO> getODetailByOID(String oID) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetail> list = dto.getODetailByOID(oID);
        ArrayList<OrderDetailDTO>dtos=new ArrayList<>();
        for (OrderDetail detail:list) {
            dtos.add(new OrderDetailDTO(detail.getOrderId(),detail.getItemcode(),detail.getDescription(),detail.getQty(),detail.getUnitPrice()));
        }
        return dtos;
    }
}
