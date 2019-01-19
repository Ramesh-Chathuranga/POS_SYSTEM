package lk.pos.fx.shop.dao.custom.impl;

import lk.pos.fx.shop.dao.CrudUtil;
import lk.pos.fx.shop.dao.custom.ItemDAO;
import lk.pos.fx.shop.entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public boolean save(Item object) throws ClassNotFoundException, SQLException {
        String sql="INSERT INTO item VALUES (?,?,?,?)";
        return CrudUtil.updateQuery(sql,object.getCode(),object.getDescription(),object.getUnitPrice(),object.getQtyOnHand())>0;

    }

    @Override
    public boolean update(Item object) throws ClassNotFoundException, SQLException {
        String sql="UPDATE item SET description=?,unitPrice=?,qtyOnHand=? WHERE code=?";
        return CrudUtil.updateQuery(sql,object.getDescription(),object.getUnitPrice(),object.getQtyOnHand(),object.getCode())>0;
    }

    @Override
    public boolean delete(String code) throws ClassNotFoundException, SQLException {
        String sql="DELETE FROM item  WHERE code=?";
        return CrudUtil.updateQuery(sql,code)>0;
    }

    @Override
    public Item search(String code) throws ClassNotFoundException, SQLException {
        String sql="SELECT * FROM item  WHERE code=?";
        ResultSet resultSet = CrudUtil.exequteQuery(sql, code);
        Item item=null;
        if(resultSet.next()){
            item=new Item(resultSet.getString(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getInt(4));
        }
        return item;
    }

    @Override
    public ArrayList<Item> getAll() throws ClassNotFoundException, SQLException {
        String sql="SELECT * FROM item";
        ResultSet resultSet = CrudUtil.exequteQuery(sql);
        ArrayList<Item>items=new ArrayList<>();
        while (resultSet.next()){
            items.add(new Item(resultSet.getString(1),
                               resultSet.getString(2),
                               resultSet.getDouble(3),
                               resultSet.getInt(4)  ) );
        }
        return items;
    }

    @Override
    public boolean ItemQtymanager(String code, int qty) throws ClassNotFoundException, SQLException{
        ItemDAOImpl dao=new ItemDAOImpl();
        Item search = dao.search(code);
        int qtyOnHand=search.getQtyOnHand()-qty;
        String sql="UPDATE item SET qtyOnHand=? WHERE code=?";
        int updateQuery = CrudUtil.updateQuery(sql, qtyOnHand, code);
        return updateQuery>0;
    }
}
