package lk.pos.fx.shop.business.Custom.impl;

import lk.pos.fx.shop.business.Custom.ItemBO;
import lk.pos.fx.shop.dao.DAOFactory;
import lk.pos.fx.shop.dao.custom.ItemDAO;
import lk.pos.fx.shop.entity.Item;
import lk.pos.fx.shop.model.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    ItemDAO dao=(ItemDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ITEM);
    @Override
    public boolean saveItem(ItemDTO dto) throws ClassNotFoundException, SQLException {
        return dao.save(new Item(dto.getCode(),dto.getDescription(),dto.getUnitPrice(),dto.getQtyOnHand()));
    }

    @Override
    public boolean updateItem(ItemDTO dto) throws ClassNotFoundException, SQLException {
        return  dao.update(new Item(dto.getCode(),dto.getDescription(),dto.getUnitPrice(),dto.getQtyOnHand()));
    }

    @Override
    public boolean deleteItem(String code) throws ClassNotFoundException, SQLException {
        return dao.delete(code);
    }

    @Override
    public ItemDTO searchItem(String code) throws ClassNotFoundException, SQLException {
        Item item = dao.search(code);
         return new ItemDTO(item.getCode(),item.getDescription(),item.getUnitPrice(),item.getQtyOnHand());
    }

    @Override
    public ArrayList<ItemDTO> getAllItem() throws ClassNotFoundException, SQLException {
        ArrayList<ItemDTO>itemDTOS=new ArrayList<>();
        ArrayList<Item>items=dao.getAll();
        for (Item item :items) {
          itemDTOS.add(new ItemDTO(item.getCode(),item.getDescription(),item.getUnitPrice(),item.getQtyOnHand()));
        }
        return itemDTOS;
    }

    @Override
    public boolean ItemQtymanager(String code, int qty) throws ClassNotFoundException, SQLException {
        return dao.ItemQtymanager(code,qty);
    }
}
