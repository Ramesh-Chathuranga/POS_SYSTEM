package lk.pos.fx.shop.dao.custom;

import lk.pos.fx.shop.dao.CustomDAO;
import lk.pos.fx.shop.entity.Item;

import java.sql.SQLException;

public interface ItemDAO extends CustomDAO<Item,String> {

    public boolean ItemQtymanager(String code,int qty)throws ClassNotFoundException, SQLException;
}
