package lk.pos.fx.shop.business.Custom;

import lk.pos.fx.shop.business.SuperBO;
import lk.pos.fx.shop.model.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    public boolean saveItem(ItemDTO dto) throws ClassNotFoundException, SQLException;
    public boolean updateItem(ItemDTO dto)throws ClassNotFoundException, SQLException;
    public boolean deleteItem(String code)throws ClassNotFoundException, SQLException;
    public ItemDTO searchItem(String code)throws ClassNotFoundException, SQLException;
    public ArrayList<ItemDTO> getAllItem()throws ClassNotFoundException, SQLException;
    public boolean ItemQtymanager(String code,int qty)throws ClassNotFoundException, SQLException;
}
