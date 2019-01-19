package lk.pos.fx.shop.business.Custom;

import lk.pos.fx.shop.business.SuperBO;
import lk.pos.fx.shop.model.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    public boolean saveCustomer(CustomerDTO dto) throws ClassNotFoundException, SQLException;
    public boolean updateCustomer(CustomerDTO dto)throws ClassNotFoundException, SQLException;
    public boolean deleteCustomer(String code)throws ClassNotFoundException, SQLException;
    public CustomerDTO searchCustomer(String code)throws ClassNotFoundException, SQLException;
    public ArrayList<CustomerDTO> getAllCustomer()throws ClassNotFoundException, SQLException;
}
