package ma.ebank.app.customerservice.service;


import ma.ebank.app.customerservice.entities.Customer;

import java.util.List;


public interface CustomerService {
    public List<Customer> getAllCustomers();
    public Customer getCustomerById(Long id);
    public Customer saveCustomer(Customer customer);
}
