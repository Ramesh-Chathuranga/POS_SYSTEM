package lk.pos.fx.shop.dao.custom.impl;

import lk.pos.fx.shop.business.BOFactory;
import lk.pos.fx.shop.business.Custom.OrderDetailBO;
import lk.pos.fx.shop.dao.CrudUtil;
import lk.pos.fx.shop.dao.DAOFactory;
import lk.pos.fx.shop.dao.custom.OrderDetailDAO;
import lk.pos.fx.shop.dao.custom.OrdersDAO;
import lk.pos.fx.shop.entity.OrderDetail;
import lk.pos.fx.shop.entity.Orders;
import lk.pos.fx.shop.model.OrderDetailDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class OrderDAOImpl implements OrdersDAO {

    OrderDetailDAO dao= (OrderDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDERDETAIL);
    @Override
    public boolean save(Orders object) throws ClassNotFoundException, SQLException {
        String sql="INSERT INTO orders VALUES (?,?,?)";
        ArrayList<OrderDetail>details=object.getOrderDetails();

        return CrudUtil.updateQuery(sql,object.getOrderId(),object.getDate(),object.getCustomerId())>0;
    }

    @Override
    public boolean update(Orders object) throws ClassNotFoundException, SQLException {
        String sql="UPDATE orders SET date=?,customerID=? WHERE id=?";
        return CrudUtil.updateQuery(sql,object.getDate(),object.getCustomerId(),object.getOrderId())>0;
    }

    @Override
    public boolean delete(String code) throws ClassNotFoundException, SQLException {
        String sql="DELETE FROM orders  WHERE id=?";
        return CrudUtil.updateQuery(sql,code)>0;
    }

    @Override
    public Orders search(String code) throws ClassNotFoundException, SQLException {
        String sql="SELECT * FROM orders  WHERE id=?";

        ResultSet resultSet = CrudUtil.exequteQuery(sql, code);
        Orders order=null;
        if(resultSet.next()){

            order=new Orders(resultSet.getString(1),
                             resultSet.getDate(2).toLocalDate(),
                             resultSet.getString(3)
                            ,dao.getODetailByOID(resultSet.getString(1)));
        }
        return order;
    }

    @Override
    public ArrayList<Orders> getAll() throws ClassNotFoundException, SQLException {
        String sql="SELECT * FROM orders";
        ResultSet resultSet = CrudUtil.exequteQuery(sql);
        ArrayList<Orders>orders=new ArrayList<>();
        while (resultSet.next()){

            orders.add( new Orders(resultSet.getString(1),
                    resultSet.getDate(2).toLocalDate(),
                    resultSet.getString(3),
                    dao.getODetailByOID(resultSet.getString(1))));

        }
        return orders;
    }
}
