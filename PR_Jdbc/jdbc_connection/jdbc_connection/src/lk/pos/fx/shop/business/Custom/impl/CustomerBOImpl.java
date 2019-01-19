package lk.pos.fx.shop.business.Custom.impl;

import lk.pos.fx.shop.business.Custom.CustomerBO;
import lk.pos.fx.shop.dao.DAOFactory;
import lk.pos.fx.shop.dao.custom.CustomDAO;
import lk.pos.fx.shop.entity.Customer;
import lk.pos.fx.shop.model.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    CustomDAO dao=(CustomDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);
    @Override
    public boolean saveCustomer(CustomerDTO dto) throws ClassNotFoundException, SQLException {

        return dao.save(new Customer(dto.getId(),dto.getName(),dto.getAddress()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws ClassNotFoundException, SQLException {
        return dao.update(new Customer(dto.getId(),dto.getName(),dto.getAddress()));
    }

    @Override
    public boolean deleteCustomer(String code) throws ClassNotFoundException, SQLException {
        return dao.delete(code);
    }

    @Override
    public CustomerDTO searchCustomer(String code) throws ClassNotFoundException, SQLException {
        Customer customer=dao.search(code);
        return new CustomerDTO(customer.getId(),customer.getName(),customer.getAddress());
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomer() throws ClassNotFoundException, SQLException {
        ArrayList<Customer>list=dao.getAll();
        ArrayList<CustomerDTO>dtos=new ArrayList<>();
        for (Customer customer:list) {
            dtos.add(new  CustomerDTO(customer.getId(),customer.getName(),customer.getAddress()) );
        }
        return dtos;
    }
}
