package ma.ebank.app.customerservice.service;

import jdk.jfr.Threshold;
import ma.ebank.app.customerservice.entities.Customer;
import ma.ebank.app.customerservice.repository.CustomerRepository;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository){
        this.customerRepository=customerRepository;
    }

    @Override
    @McpTool(description = "Get All customers")
    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    @Override
    @McpTool(description = "Find customer by id ")
    public Customer getCustomerById(@McpToolParam(description = "the customer id") Long id) {
        return this.customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Override
    @McpTool(description = "Save new Customer")
    public Customer saveCustomer( @McpToolParam(description = "the customer to save (name , email)")
                                      Customer customer) {

        return this.customerRepository.save(customer);
    }

}
