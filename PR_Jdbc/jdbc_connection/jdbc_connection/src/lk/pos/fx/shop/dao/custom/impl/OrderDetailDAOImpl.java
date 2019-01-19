package lk.pos.fx.shop.dao.custom.impl;

import lk.pos.fx.shop.dao.CrudUtil;
import lk.pos.fx.shop.dao.custom.OrderDetailDAO;
import lk.pos.fx.shop.entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAOImpl implements OrderDetailDAO {

    @Override
    public boolean save(ArrayList<OrderDetail> details) throws SQLException, ClassNotFoundException {
        System.out.println(details.size()+"    this DAO");
       try{
           String sql="INSERT INTO order_detail VALUES (?,?,?,?,?)";
           for (OrderDetail detail:details) {
               CrudUtil.updateQuery(sql,detail.getOrderId(),detail.getItemcode(),detail.getDescription(),detail.getQty(),detail.getUnitPrice());
           }

       }catch (RuntimeException e){
           return false;
       }
     return true;
    }

    @Override
    public ArrayList<OrderDetail> getAllOdetails() throws SQLException, ClassNotFoundException {
        String sql="SELECT * FROM order_detail ";
        ResultSet resultSet = CrudUtil.exequteQuery(sql);
        ArrayList<OrderDetail>orderDetails=new ArrayList<>();
        while (resultSet.next()){
            orderDetails.add(new OrderDetail(resultSet.getString(1),
                                             resultSet.getString(2),
                                             resultSet.getString(3),
                                             resultSet.getInt(4),
                                             resultSet.getDouble(5)) );
        }
        return orderDetails;
    }

    @Override
    public ArrayList<OrderDetail> getODetailByOID(String oID)throws SQLException, ClassNotFoundException {
        String sql="SELECT * FROM order_detail WHERE oid=? ";
        ResultSet resultSet = CrudUtil.exequteQuery(sql,oID);
        ArrayList<OrderDetail>orderDetails=new ArrayList<>();
        while (resultSet.next()){
            orderDetails.add(new OrderDetail(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getDouble(5)) );
        }
        return orderDetails;
    }
}
