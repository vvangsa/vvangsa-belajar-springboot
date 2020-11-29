package id.vvangsa.service;

import id.vvangsa.model.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomer();

    Customer doSaveOrUpdate(Customer customer);

    Customer getCustomerBy(Integer id);

    void doDeleteBy(Integer id);

}
