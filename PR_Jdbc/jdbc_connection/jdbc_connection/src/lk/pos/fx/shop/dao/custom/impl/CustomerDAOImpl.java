package lk.pos.fx.shop.dao.custom.impl;

import lk.pos.fx.shop.dao.custom.CustomDAO;
import lk.pos.fx.shop.db.Connection.DBconnection;
import lk.pos.fx.shop.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomDAO {
    @Override
    public boolean save(Customer object) throws ClassNotFoundException, SQLException {
        String sql="INSERT INTO customer VALUES (?,?,?)";
        Connection connection= DBconnection.getInstance().getConnection();
        PreparedStatement statement=connection.prepareStatement(sql);
        statement.setObject(1,object.getId());
        statement.setObject(2,object.getName());
        statement.setObject(3,object.getAddress());
        int i = statement.executeUpdate();
        return i>0;
    }

    @Override
    public boolean update(Customer object) throws ClassNotFoundException, SQLException {
        String sql="UPDATE customer SET name=?,address=? WHERE id=?";
        Connection connection= DBconnection.getInstance().getConnection();
        PreparedStatement statement=connection.prepareStatement(sql);
        statement.setObject(3,object.getId());
        statement.setObject(1,object.getName());
        statement.setObject(2,object.getAddress());
        return statement.executeUpdate()>0;
    }

    @Override
    public boolean delete(String code) throws ClassNotFoundException, SQLException {
        String sql="DELETE FROM customer  WHERE id=?";
        Connection connection= DBconnection.getInstance().getConnection();
        PreparedStatement statement=connection.prepareStatement(sql);
        statement.setObject(1,code);
        return statement.executeUpdate()>0;
    }

    @Override
    public Customer search(String code) throws ClassNotFoundException, SQLException {
        String sql="SELECT * FROM customer  WHERE id=?";
        Connection connection= DBconnection.getInstance().getConnection();
        PreparedStatement statement=connection.prepareStatement(sql);
        statement.setObject(1,code);
        ResultSet set = statement.executeQuery();
        if(set.next()){
            Customer customer=new Customer(set.getString("id"),set.getString("name"),set.getString("address"));
            return customer;
        }

        return null;
    }

    @Override
    public ArrayList<Customer> getAll() throws ClassNotFoundException, SQLException {
        String sql="SELECT * FROM customer";
        Connection connection= DBconnection.getInstance().getConnection();
        ArrayList<Customer>customers=new ArrayList<>();
        PreparedStatement statement=connection.prepareStatement(sql);
        ResultSet set = statement.executeQuery();
        while (set.next()){
            customers.add( new Customer(set.getString(1),set.getString(2),set.getString(3))  );
        }

        return customers;
    }
}
