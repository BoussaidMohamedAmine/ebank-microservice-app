package ma.ebank.app.ebankservice.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import ma.ebank.app.ebankservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name= "customer-service")
public interface CustomerRestClient {

    @GetMapping("/customers/{id}")
    @CircuitBreaker(name="customerService" ,fallbackMethod = "getDefaultCustomer")
    Customer getCustomerById(@PathVariable("id") Long id);

    default Customer getDefaultCustomer(Long id , Exception exception){
        return new Customer(id , "NoName" , "NoAddress");
    }
}
